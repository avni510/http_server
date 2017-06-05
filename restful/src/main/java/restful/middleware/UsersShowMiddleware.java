package restful.middleware;

import core.DataStore;
import core.Middleware;

import core.request.Request;

import core.response.Response;
import restful.handler.users.UsersShowHandler;


public class UsersShowMiddleware implements Middleware {
  private DataStore<Integer, String> dataStore;

  public UsersShowMiddleware(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  public Response call(Request request) throws Exception {
    UsersShowHandler usersShowHandler = new UsersShowHandler(dataStore);
    return usersShowHandler.generate(request);
  }
}
