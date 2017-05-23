package http_server;

import http_server.request.RequestMethod;

import java.util.HashMap;
import java.util.Map;

public class Router {
  private Map<Tuple<Enum<RequestMethod>, String>, Handler> routes = new HashMap();

  public Map<Tuple<Enum<RequestMethod>, String>, Handler> getRoutes(){
    return routes;
  }

  public Router addRoute(Enum<RequestMethod> requestMethod, String uri, Handler handler) {
    routes.put(new Tuple<>(requestMethod, uri), handler);
    return this;
  }

  public Handler retrieveHandler(Enum<RequestMethod> requestMethod, String uri){
    if (uri.contains("?")) {
      String[] uriParts = uri.split("\\?");
      return routes.get(new Tuple<>(requestMethod, uriParts[0]));
    } else {
      return routes.get(new Tuple<>(requestMethod, uri));
    }
  }

  public boolean uriExists(String uri){
    boolean found = false;
    for(Map.Entry<Tuple<Enum<RequestMethod>, String>, Handler> entry : routes.entrySet()){
      String uriInRoute = entry.getKey().getSecondElement();
      if (uriInRoute.equals(uri)){
        return true;
      }
    }
    return found;
  }
}
