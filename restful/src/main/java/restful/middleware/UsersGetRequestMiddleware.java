package restful.middleware;

import core.DataStore;
import core.Handler;
import core.Middleware;

import core.request.Request;
import core.response.Response;

import restful.handler.users.UsersGetHandler;

public class UsersGetRequestMiddleware implements Middleware{
  private DataStore<Integer, String> dataStore;

  public UsersGetRequestMiddleware(DataStore<Integer, String> dataStore) {
    this.dataStore = dataStore;
  }
  public Response call(Request request) throws Exception {
    Handler handler = new UsersGetHandler(dataStore);
    return handler.generate(request);
  }
}
