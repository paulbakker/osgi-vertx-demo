package chat.vertx.messaging.storage.mongo;

import java.util.List;

import org.apache.felix.dm.annotation.api.Component;
import org.apache.felix.dm.annotation.api.ServiceDependency;
import org.apache.felix.dm.annotation.api.Start;

import chat.api.ChatMessage;
import chat.vertx.messaging.storage.MessageStore;
import chat.vertx.mongo.MongoService;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;
import rx.Observable;

@Component
public class MongoMessageStorage implements MessageStore {
	@ServiceDependency
	private volatile Vertx vertx;
	
	@ServiceDependency
	private volatile MongoService mongoService;
	
	
	@Start
	public void start() {
		vertx.eventBus().consumer("chat").toObservable()
			.map(m -> (String)m.body())
			.map(m -> new JsonObject(m))
			.flatMap(m -> mongoService.getClient().saveObservable("messages", m)).subscribe();
	}


	@Override
	public Observable<List<JsonObject>> listMessagesAsJson() {
		return mongoService.getClient().findObservable("messages", new JsonObject());
	}


	@Override
	public Observable<List<ChatMessage>> listMessages() {
		return listMessagesAsJson()
				.flatMap(m -> Observable.from(m))
				.map(m -> new ChatMessage(m.getLong("timestamp"), m.getString("user"), m.getString("text")))
				.toList();
			
	}
	
	@Override
	public Observable<Void> clear() {
		return mongoService.getClient().removeObservable("messages", new JsonObject());
	}
}
