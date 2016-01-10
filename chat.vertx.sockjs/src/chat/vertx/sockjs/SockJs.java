package chat.vertx.sockjs;

import java.util.HashSet;
import java.util.Set;

import org.apache.felix.dm.annotation.api.Component;
import org.apache.felix.dm.annotation.api.ServiceDependency;
import org.apache.felix.dm.annotation.api.Start;
import org.apache.felix.dm.annotation.api.Stop;

import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.web.Route;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.sockjs.SockJSHandler;

@Component
public class SockJs {
	@ServiceDependency
	private volatile Vertx vertx;
	
	@ServiceDependency
	private volatile Router router;
	
	private final Set<Route> routes = new HashSet<>();
	
	@Start
	public void start() {
		SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
		BridgeOptions options = new BridgeOptions();
		PermittedOptions chatPermission = new PermittedOptions().setAddress("chat");
		PermittedOptions outboundPermitted1 = new PermittedOptions().setAddress("chat");
		
		options.addInboundPermitted(chatPermission);
		options.addOutboundPermitted(outboundPermitted1);
		sockJSHandler.bridge(options);

		Route route = router.route("/eventbus/*");
		routes.add(route);
		route.handler(sockJSHandler);
	}
	
	@Stop
	public void stop() {
		routes.forEach(r -> r.remove());
	}
}
