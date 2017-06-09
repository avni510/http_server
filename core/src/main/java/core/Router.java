package core;

import core.request.RequestMethod;

import core.utils.Tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Router {
  private Map<Tuple<Enum<RequestMethod>, String>, Handler> routes = new HashMap();

  public Router addRoute(Enum<RequestMethod> requestMethod, String path, Handler handler) {
    if (!isStaticRoute(path)) {
      String regexPath = convertPathToRegex(path);
      routes.put(new Tuple<>(requestMethod, regexPath), handler);
      return this;
    }
    routes.put(new Tuple<>(requestMethod, path), handler);
    return this;
  }

  public Handler retrieveHandler(Enum<RequestMethod> requestMethod, String uri) {
    if (uri.contains("?")) {
      uri = getUriWithoutQueryParams(uri);
    }
    for (Map.Entry<Tuple<Enum<RequestMethod>, String>, Handler> route :
        routes.entrySet()) {
      Tuple<Enum<RequestMethod>, String> requestMethodAndPath = route.getKey();
      Enum<RequestMethod> routeRequestMethod = requestMethodAndPath.getFirstElement();
      String routePath = requestMethodAndPath.getSecondElement();
      if (routeRequestMethod.equals(requestMethod) && Pattern.matches(routePath, uri)) {
        Handler handler = route.getValue();
        return handler;
      }
    }
    return null;
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

  private boolean isStaticRoute(String path) {
    if (path.contains(":")) {
      return false;
    }
    return true;
  }

  private String convertPathToRegex(String path) {
    String regex = "[a-zA-Z0-9]+";
    String[] splitPath = path.split("/");
    for (int i = 0; i < splitPath.length; i++) {
      if (splitPath[i].contains(":")) {
        splitPath[i] = regex;
      }
    }
    return String.join("/", splitPath);
  }
}
