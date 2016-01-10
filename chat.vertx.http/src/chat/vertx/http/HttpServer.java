package chat.vertx.http;

import java.util.HashSet;
import java.util.Set;

import org.apache.felix.dm.annotation.api.Component;
import org.apache.felix.dm.annotation.api.ServiceDependency;
import org.apache.felix.dm.annotation.api.Start;
import org.apache.felix.dm.annotation.api.Stop;

import io.vertx.core.http.HttpMethod;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.web.Route;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.CorsHandler;
import io.vertx.rxjava.ext.web.handler.StaticHandler;

@Component
public class HttpServer {

	@ServiceDependency
	private volatile Vertx vertx;
	
	@ServiceDependency
	private volatile Router router;
	
	private final Set<Route> routes = new HashSet<>();
	
	private volatile io.vertx.rxjava.core.http.HttpServer server;
	
	@Start
	public void start() {
		router.route().handler(CorsHandler.create("*")
	    	      .allowedMethod(HttpMethod.GET)
	    	      .allowedMethod(HttpMethod.POST)
	    	      .allowedMethod(HttpMethod.DELETE)
	    	      .allowedMethod(HttpMethod.PUT)
	    	      .allowedMethod(HttpMethod.OPTIONS)
	    	      .allowedHeader("X-PINGARUNER")
	    	      .allowedHeader("Content-Type"));
		Route route = router.route("/static/*");
		route.handler(StaticHandler.create());
		routes.add(route);
		
		server = vertx.createHttpServer();
		server.requestHandler(router::accept).listen(8080);		
	}
	
	@Stop
	public void stop() {
		routes.forEach(r -> r.remove());
		server.close();
		server = null;
	}
}
