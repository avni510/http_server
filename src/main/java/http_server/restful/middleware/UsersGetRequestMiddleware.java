package http_server.restful.middleware;

import http_server.DataStore;
import http_server.Handler;
import http_server.Middleware;
import http_server.request.Request;
import http_server.response.Response;
import http_server.restful.handler.users.UsersGetHandler;

public class UsersGetRequestMiddleware implements Middleware{
  private DataStore dataStore;

  public UsersGetRequestMiddleware(DataStore dataStore) {
    this.dataStore = dataStore;
  }
  public Response call(Request request) throws Exception {
    Handler handler = new UsersGetHandler(dataStore);
    return handler.generate(request);
  }
}
