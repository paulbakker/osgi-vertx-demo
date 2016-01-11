# osgi-vertx-demo

This is the example repo that comes together with a [blog post](http://paulbakker.io/osgi/vertx-osgi/) describing how to run Vertx Web with OSGi. Please refer to the blog post for all the details of how this project is set up.

The following is a list of bundles (modules) which make the application. The workspace is created in Bndtools, a plugin for Eclipse to make OSGi development easy. Out-of-the-box we also get a Gradle build for each Bndtools workspace, which we use on build servers. During development, we don’t need to run Gradle however.

* chat.api : API only project, containing the ChatMessage class.
* chat.vertx.bootstrap : Wraps Vertx Web and vertx RX and makes the Vertx and Router instance available as OSGi services.
* chat.vertx.http : Setup static file routing and starts HTTP server.
* chat.vertx.messaging.storage.mongo : Stores chat messages in Mongo
* chat.vertx.messaging.storage.rest : Makes the stored chat messages available in a RESTful web service
* chat.vertx.messaging.storage.itest : OSGi integration test for the Mongo service.
* chat.vertx.mongo : Sets up a MongoClient based on Configuration Admin configuration and publishes it as an OSGi service.
* chat.vertx.sockjs : Sets up the SockJS event bus bridge.
* run : Contains the configuration files, static web resources and bndrun configuration to start the application. Check the chat.bndrun file for the full list of bundles to run the application. The application can also be started from this file directly.
