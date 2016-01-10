package chat.vertx.bootstrap;

import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;

import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.eventbus.EventBus;
import io.vertx.rxjava.ext.web.Router;

public class VertxActivator extends DependencyActivatorBase{

	private volatile Vertx vertx;
	
	@Override
	public void init(BundleContext arg0, DependencyManager dm) throws Exception {
		vertx = Vertx.vertx();
		
		dm.add(createComponent().setInterface(Vertx.class.getName(), null).setImplementation(vertx));
		dm.add(createComponent().setInterface(EventBus.class.getName(), null).setImplementation(vertx.eventBus()));
		
		Router router = Router.router(vertx);
		dm.add(createComponent().setInterface(Router.class.getName(), null).setImplementation(router));
	}

	@Override
	public void destroy(BundleContext context, DependencyManager manager) throws Exception {
		vertx.close();
		vertx = null; 
	}
	
	

}
