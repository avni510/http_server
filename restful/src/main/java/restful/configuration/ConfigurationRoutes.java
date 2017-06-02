package restful.configuration;

import core.DataStore;
import core.Router;

import restful.handler.users.UsersGetHandler;
import restful.handler.users.UsersPostHandler;

import core.request.RequestMethod;

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
