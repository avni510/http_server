package http_server.restful.middleware;

import http_server.DataStore;
import http_server.ErrorHandler;
import http_server.Handler;
import http_server.Middleware;

import http_server.request.Request;

import http_server.response.Response;

public class ValidIdMiddleware implements Middleware{
  private Middleware app;
  private DataStore<Integer, String> dataStore;

  public ValidIdMiddleware(DataStore<Integer, String> datastore, Middleware app) {
    this.dataStore = datastore;
    this.app = app;
  }

  public Response call(Request request) throws Exception {
    if (!dataStoreHasId(request)) {
      Handler handler = new ErrorHandler(404);
      return handler.generate(request);
    } else {
      return app.call(request);
    }
  }


  private boolean dataStoreHasId(Request request){
    Integer id = request.getIdInUri();
    return dataStore.keyExists(id);
  }
}
