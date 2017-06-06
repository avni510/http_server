package restful.middleware;

import core.DataStore;
import core.Handler;
import core.Middleware;

import core.request.Request;
import core.request.RequestMethod;

import core.response.Response;

import restful.handler.users.UsersPutHandler;

public class UsersPutRequestMiddleware implements Middleware {
  private Middleware app;
  private DataStore<Integer, String> dataStore;

  public UsersPutRequestMiddleware(DataStore<Integer, String> dataStore, Middleware app) {
    this.dataStore = dataStore;
    this.app = app;
  }

  public Handler call(Request request) throws Exception {
    if (request.getRequestMethod().equals(RequestMethod.PUT)) {
      return new UsersPutHandler(dataStore);
    } else {
      return app.call(request);
    }
  }
}
