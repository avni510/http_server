package restful.configuration;

import core.DataStore;

import core.Router;

import core.handler.BaseHandler;

import restful.handler.users.UsersEditHandler;

import core.request.RequestMethod;

import restful.handler.users.UsersIndexHandler;
import restful.handler.users.UsersNewHandler;
import restful.handler.users.UsersShowHandler;
import restful.handler.users.UsersPostHandler;
import restful.handler.users.UsersPutHandler;
import restful.handler.users.UsersDeleteHandler;

public class ConfigurationRoutes {
  private DataStore<Integer, String> dataStore;

  public ConfigurationRoutes(DataStore<Integer, String> dataStore) {
    this.dataStore = dataStore;
  }

  public Router buildRouter() {
    Router router = new Router();
    router.addRoute(RequestMethod.GET, "/", new BaseHandler())
          .addRoute(RequestMethod.GET, "/users", new UsersIndexHandler(dataStore))
          .addRoute(RequestMethod.GET, "/users/new", new UsersNewHandler())
          .addRoute(RequestMethod.POST, "/users", new UsersPostHandler(dataStore))
          .addRoute(RequestMethod.GET, "/users/:id", new UsersShowHandler(dataStore))
          .addRoute(RequestMethod.GET, "/users/:id/edit", new UsersEditHandler(dataStore))
          .addRoute(RequestMethod.PUT, "/users/:id", new UsersPutHandler(dataStore))
          .addRoute(RequestMethod.DELETE, "/users/:id", new UsersDeleteHandler(dataStore));
    return router;
  }
}
