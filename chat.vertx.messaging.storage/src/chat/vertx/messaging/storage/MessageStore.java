package chat.vertx.messaging.storage;

import java.util.List;

import chat.api.ChatMessage;
import io.vertx.core.json.JsonObject;
import rx.Observable;

public interface MessageStore {

	Observable<List<JsonObject>> listMessagesAsJson();
	Observable<List<ChatMessage>> listMessages();
	Observable<Void> clear();
}
