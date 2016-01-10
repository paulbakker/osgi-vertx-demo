package chat.vertx.mongo;

import io.vertx.rxjava.ext.mongo.MongoClient;

public interface MongoService {
	MongoClient getClient();
}
