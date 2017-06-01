package http_server.restful.configuration;

import http_server.core.DataStore;
import http_server.core.Router;

import http_server.restful.handler.users.UsersGetHandler;
import http_server.restful.handler.users.UsersPostHandler;

import http_server.core.request.RequestMethod;

public class ConfigurationRoutes {
  private DataStore<Integer, String> dataStore;

  public ConfigurationRoutes(DataStore<Integer, String> dataStore) {
    this.dataStore = dataStore;
  }

  public Router buildRouter() {
    Router router = new Router();
    router.addRoute(RequestMethod.GET, "/users", new UsersGetHandler(dataStore))
          .addRoute(RequestMethod.GET, "/users/new", new UsersGetHandler(dataStore))
          .addRoute(RequestMethod.POST, "/users", new UsersPostHandler(dataStore));
    return router;
  }
}
