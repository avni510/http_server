package http_server.restful.middleware;

import http_server.core.DataStore;
import http_server.core.Handler;
import http_server.core.Middleware;

import http_server.core.request.Request;
import http_server.core.request.RequestMethod;

import http_server.core.response.Response;

import http_server.restful.handler.users.UsersDeleteHandler;

public class UsersDeleteRequestMiddleware implements Middleware{
  private Middleware app;
  private DataStore<Integer, String> dataStore;

  public UsersDeleteRequestMiddleware(DataStore<Integer, String> dataStore, Middleware app) {
    this.dataStore = dataStore;
    this.app = app;
  }

  public Response call(Request request) throws Exception {
    if(request.getRequestMethod().equals(RequestMethod.DELETE)) {
      Handler handler = new UsersDeleteHandler(dataStore);
      return handler.generate(request);
    } else {
      return app.call(request);
    }
  }
}
