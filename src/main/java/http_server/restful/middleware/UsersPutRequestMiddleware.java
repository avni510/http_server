package http_server.restful.middleware;

import http_server.DataStore;
import http_server.Handler;
import http_server.Middleware;
import http_server.request.Request;
import http_server.request.RequestMethod;
import http_server.response.Response;
import http_server.restful.handler.users.UsersPutHandler;

public class UsersPutRequestMiddleware implements Middleware {
  private Middleware app;
  private DataStore dataStore;

  public UsersPutRequestMiddleware(DataStore dataStore, Middleware app) {
    this.dataStore = dataStore;
    this.app = app;
  }

  public Response call(Request request) throws Exception {
    if (request.getRequestMethod().equals(RequestMethod.PUT)) {
      Handler handler = new UsersPutHandler(dataStore);
      return handler.generate(request);
    } else {
      return app.call(request);
    }
  }
}
