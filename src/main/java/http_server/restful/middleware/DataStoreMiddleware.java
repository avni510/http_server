package http_server.restful.middleware;

import http_server.DataStore;
import http_server.ErrorHandler;
import http_server.Handler;
import http_server.Middleware;
import http_server.request.Request;
import http_server.request.RequestMethod;
import http_server.response.Response;
import http_server.restful.handler.users.UsersDeleteHandler;
import http_server.restful.handler.users.UsersGetHandler;
import http_server.restful.handler.users.UsersPutHandler;

public class DataStoreMiddleware implements Middleware{
  private DataStore dataStore;
  private Middleware nextMiddleware;

  public DataStoreMiddleware(DataStore dataStore, Middleware nextMiddleware) {
    this.dataStore = dataStore;
    this.nextMiddleware = nextMiddleware;
  }

  public Response call(Request request) throws Exception {
    Handler handler;
    if (request.getRequestMethod().equals(RequestMethod.PUT)) {
      if(dataStoreHasId(request)) {
        handler = new UsersPutHandler(dataStore);
        return handler.generate(request);
      }
      else {
        handler = new ErrorHandler(404);
        return handler.generate(request);
      }
    } else if(request.getRequestMethod().equals(RequestMethod.DELETE)) {
      if(dataStoreHasId(request)) {
        handler = new UsersDeleteHandler(dataStore);
        return handler.generate(request);
      }
      else {
        handler = new ErrorHandler(404);
        return handler.generate(request);
      }
    } else if(request.getRequestMethod().equals(RequestMethod.GET)) {
      if(dataStoreHasId(request)) {
        handler = new UsersGetHandler(dataStore);
        return handler.generate(request);
      } else {
        handler = new ErrorHandler(404);
        return handler.generate(request);
      }
    }
    return null;
  }

  private boolean dataStoreHasId(Request request){
    String id = request.getIdInUri();
    return dataStore.keyExists(id);
  }
}
