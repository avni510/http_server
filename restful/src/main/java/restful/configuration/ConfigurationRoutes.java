package restful.configuration;

import core.DataStore;

import core.Router;

import core.handler.BaseHandler;

import restful.handler.users.UsersIndexHandler;
import restful.handler.users.UsersPostHandler;
import restful.handler.users.UsersSuccessHandler;

import core.request.RequestMethod;

public class ConfigurationRoutes {
  private DataStore<Integer, String> dataStore;

  public ConfigurationRoutes(DataStore<Integer, String> dataStore) {
    this.dataStore = dataStore;
  }

  public Router buildRouter() {
    Router router = new Router();
    router.addRoute(RequestMethod.GET, "/users", new UsersIndexHandler(dataStore))
          .addRoute(RequestMethod.GET, "/users/new", new UsersSuccessHandler())
          .addRoute(RequestMethod.POST, "/users", new UsersPostHandler(dataStore))
          .addRoute(RequestMethod.GET, "/", new BaseHandler());
    return router;
  }
}
