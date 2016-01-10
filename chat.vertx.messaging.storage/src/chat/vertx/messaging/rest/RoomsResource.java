package chat.vertx.messaging.rest;

import java.util.HashSet;
import java.util.Set;

import org.apache.felix.dm.annotation.api.Component;
import org.apache.felix.dm.annotation.api.ServiceDependency;
import org.apache.felix.dm.annotation.api.Start;
import org.apache.felix.dm.annotation.api.Stop;

import chat.vertx.messaging.storage.MessageStore;
import io.vertx.core.json.Json;
import io.vertx.rxjava.ext.web.Route;
import io.vertx.rxjava.ext.web.Router;

@Component
public class RoomsResource {
	
	@ServiceDependency
	private volatile Router router;

	private final Set<Route> routes = new HashSet<>();
	
	@ServiceDependency
	private volatile MessageStore messageStore;
	
	@Start
	public void start() {
		Route route = router.get("/messages");
	    routes.add(route);
	
		route.handler(routingContext -> {
			messageStore.listMessagesAsJson().subscribe(
					messages -> routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setChunked(true).write(Json.encodePrettily(messages)), 
					e -> { 
						routingContext.fail(500);
						e.printStackTrace();
					},
					() -> routingContext.response().end());
		});
		
	}
	
	@Stop
	public void stop() {
		routes.forEach(r -> r.remove());
	}
	
}
