package chat.vertx.messaging.storage.test;

import static org.amdatu.testing.configurator.TestConfigurator.cleanUp;
import static org.amdatu.testing.configurator.TestConfigurator.configure;
import static org.amdatu.testing.configurator.TestConfigurator.createConfiguration;
import static org.amdatu.testing.configurator.TestConfigurator.createServiceDependency;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import chat.api.ChatMessage;
import chat.vertx.messaging.storage.MessageStore;
import rx.Observable;
import rx.observers.TestSubscriber;
import io.vertx.rxjava.core.*;
import io.vertx.core.json.*;

public class MessageStoreTest {

	private volatile MessageStore serviceToTest;
	private volatile Vertx vertx;

	@Before
	public void setup() {
		configure(this)
			.add(createServiceDependency().setService(Vertx.class).setRequired(true))	
			.add(createConfiguration("vertx.mongo").set("url", "mongodb://localhost:27017").set("db", "vertxchat"))
			.add(createServiceDependency().setService(MessageStore.class).setRequired(true)).apply();
		
		serviceToTest.clear().toBlocking().subscribe();
	}
	
	@Test
	public void test() throws InterruptedException {
		
		ChatMessage msg1 = new ChatMessage(1, "User 1", "Hello");
		ChatMessage msg2 = new ChatMessage(2, "User 2", "Hello to you too!");
		ChatMessage msg3 = new ChatMessage(3, "User 1", "Chatting is fun");
		List<ChatMessage> msgList = Arrays.asList(msg1, msg2, msg3);
		Observable.from(msgList)
			.map(m -> Json.encode(m))
			.subscribe(m -> vertx.eventBus().publish("chat", m));
		TimeUnit.SECONDS.sleep(1);
		
		TestSubscriber<ChatMessage> testSubscriber = new TestSubscriber<>();
		serviceToTest.listMessages().flatMap(m -> Observable.from(m)).subscribe(testSubscriber);

		testSubscriber.awaitTerminalEvent();
		testSubscriber.assertNoErrors();
		testSubscriber.assertCompleted();
		testSubscriber.assertValueCount(3);
	}

	@After
	public void after() {
		cleanUp(this);
	}
}