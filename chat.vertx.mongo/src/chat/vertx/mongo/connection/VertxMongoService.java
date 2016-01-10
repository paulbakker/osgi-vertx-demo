package chat.vertx.mongo.connection;

import java.util.Dictionary;

import org.apache.felix.dm.annotation.api.Component;
import org.apache.felix.dm.annotation.api.ConfigurationDependency;
import org.apache.felix.dm.annotation.api.ServiceDependency;
import org.apache.felix.dm.annotation.api.Start;
import org.apache.felix.dm.annotation.api.Stop;

import chat.vertx.mongo.MongoService;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.mongo.MongoClient;

@Component
public class VertxMongoService implements MongoService {

	@ServiceDependency
	private volatile Vertx vertx;
	
	private volatile JsonObject mongoconfig;
	private volatile MongoClient client;
	
	@Start
	public void start() {
		connect();
	}
	
	@Stop
	public void stop() {
		client.close();
		client = null;
	}

	@Override
	public MongoClient getClient() {
		return client;
	}
	
	@ConfigurationDependency(pid="vertx.mongo")
	public void updated(Dictionary<String, Object> properties) {
		if(properties != null ) {
			
			 mongoconfig = new JsonObject()
				        .put("connection_string", properties.get("url"))
				        .put("db_name", properties.get("db"));		
			connect();
		}
	}

	private void connect() {
		if(vertx != null) {
			client = MongoClient.createShared(vertx, mongoconfig);
		}
	}
	
}
