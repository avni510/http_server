package restful.configuration;

import core.Router;

import core.handler.BaseHandler;

import core.request.RequestMethod;

import core.utils.DataStore;

import restful.handler.users.IndexUsersHandler;
import restful.handler.users.EditUsersHandler;
import restful.handler.users.NewUsersHandler;
import restful.handler.users.ShowUsersHandler;
import restful.handler.users.CreateUsersHandler;
import restful.handler.users.UpdateUsersHandler;
import restful.handler.users.DeleteUsersHandler;

public class ConfigurationRoutes {
  private DataStore<Integer, String> dataStore;

  public ConfigurationRoutes(DataStore<Integer, String> dataStore) {
    this.dataStore = dataStore;
  }

  public Router buildRouter() {
    Router router = new Router();
    router.addRoute(RequestMethod.GET, "/", new BaseHandler())
        .addRoute(RequestMethod.GET, "/users", new IndexUsersHandler(dataStore))
        .addRoute(RequestMethod.GET, "/users/new", new NewUsersHandler())
        .addRoute(RequestMethod.POST, "/users", new CreateUsersHandler(dataStore))
        .addRoute(RequestMethod.GET, "/users/:id", new ShowUsersHandler(dataStore))
        .addRoute(RequestMethod.GET, "/users/:id/edit", new EditUsersHandler(dataStore))
        .addRoute(RequestMethod.PUT, "/users/:id", new UpdateUsersHandler(dataStore))
        .addRoute(RequestMethod.DELETE, "/users/:id", new DeleteUsersHandler(dataStore));
    return router;
  }
}
