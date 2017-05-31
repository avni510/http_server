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
    Handler handler = null;
    if (request.getRequestMethod().equals(RequestMethod.PUT)) {
      if(idExists(request.getUri())) {
        handler = new UsersPutHandler(dataStore);
        return handler.generate(request);
      }
      else {
        handler = new ErrorHandler(404);
        return handler.generate(request);
      }
    } else if(request.getRequestMethod().equals(RequestMethod.DELETE)) {
      if(idExists(request.getUri())) {
        handler = new UsersDeleteHandler(dataStore);
        return handler.generate(request);
      }
      else {
        handler = new ErrorHandler(404);
        return handler.generate(request);
      }
    } else if(request.getRequestMethod().equals(RequestMethod.GET)) {
      if(idExists(request.getUri())) {
        handler = new UsersGetHandler(dataStore);
        return handler.generate(request);
      } else {
        handler = new ErrorHandler(404);
        return handler.generate(request);
      }
    }
    return null;
  }

  private boolean idExists(String uri){
    if (uri.contains("edit")) {
      String id = getIdForEdit(uri);
      return dataStore.keyExists(id);
    }
    String id = getId(uri);
    return dataStore.keyExists(id);
  }

  private String getIdForEdit(String uri){
    String[] uriParts = uri.split("/");
    return uriParts[uriParts.length - 2];
  }

  private String getId(String uri){
    String[] uriParts = uri.split("/");
    return uriParts[uriParts.length - 1];
  }
}
