package restful.middleware;

import core.DataStore;
import core.Handler;
import core.Middleware;

import core.request.Request;

import core.handler.ErrorHandler;

public class ValidIdMiddleware implements Middleware{
  private Middleware app;
  private DataStore<Integer, String> dataStore;

  public ValidIdMiddleware(DataStore<Integer, String> datastore, Middleware app) {
    this.dataStore = datastore;
    this.app = app;
  }

  public Handler call(Request request) throws Exception {
    if (!dataStoreHasId(request)) {
      return new ErrorHandler(404);
    } else {
      return app.call(request);
    }
  }

  private boolean dataStoreHasId(Request request){
    Integer id = request.getIdInUri();
    return dataStore.keyExists(id);
  }
}
