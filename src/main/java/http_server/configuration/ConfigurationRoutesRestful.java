package http_server.configuration;

import http_server.DataStore;

import http_server.Router;

import http_server.handler.users.UsersGetHandler;
import http_server.handler.users.UsersPostHandler;

import http_server.request.RequestMethod;

public class ConfigurationRoutesRestful {
  private DataStore dataStore;

  public ConfigurationRoutesRestful(DataStore dataStore) {
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
