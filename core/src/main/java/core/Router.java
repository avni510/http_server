package core;

import core.request.RequestMethod;

import java.util.HashMap;
import java.util.Map;

public class Router {
  private Map<Tuple<Enum<RequestMethod>, String>, Handler> routes = new HashMap();

  public Router addRoute(Enum<RequestMethod> requestMethod, String uri, Handler handler) {
    routes.put(new Tuple<>(requestMethod, uri), handler);
    return this;
  }

  public Handler retrieveHandler(Enum<RequestMethod> requestMethod, String uri) {
    if (uri.contains("?")) {
      String uriWithoutQueryParams = getUriWithoutQueryParams(uri);
      return routes.get(new Tuple<>(requestMethod, uriWithoutQueryParams));
    } else {
      Handler handler = routes.get(new Tuple<>(requestMethod, uri));
      if (handler == null) {
        String dynamicUri = getDynamicUri(uri);
        handler = routes.get(new Tuple<>(requestMethod, dynamicUri));
      }
      return handler;
    }
  }

  public boolean uriExists(String uri) {
    for (Map.Entry<Tuple<Enum<RequestMethod>, String>, Handler> route : routes.entrySet()) {
      String uriInRouter = route.getKey().getSecondElement();
      if (uriInRouter.equals(uri)) {
        return true;
      }
    }
    return false;
  }

  private String getUriWithoutQueryParams(String uri) {
    String[] uriParts = uri.split("\\?");
    return uriParts[0];
  }

  private String getDynamicUri(String uri) {
    if (uri.contains("edit")) {
      return getDynamicUriForEdit(uri);
    }
    return uri.substring(0, uri.length() - 1) + ":id";
  }

  private String getDynamicUriForEdit(String uri) {
    String[] uriParts = uri.split("/");
    uriParts[uriParts.length - 2] = ":id";
    return String.join("/", uriParts);
  }
}
