package restful.middleware;

import core.DataStore;
import core.Handler;
import core.Middleware;

import core.request.Request;
import core.request.RequestMethod;

import core.response.Response;

import restful.handler.users.UsersDeleteHandler;

public class UsersDeleteRequestMiddleware implements Middleware{
  private Middleware app;
  private DataStore<Integer, String> dataStore;

  public UsersDeleteRequestMiddleware(DataStore<Integer, String> dataStore, Middleware app) {
    this.dataStore = dataStore;
    this.app = app;
  }

  public Handler call(Request request) throws Exception {
    if(request.getRequestMethod().equals(RequestMethod.DELETE)) {
      return new UsersDeleteHandler(dataStore);
    } else {
      return app.call(request);
    }
  }
}
