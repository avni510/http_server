package http_server;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class Router {
  private static Map<Tuple<Enum<RequestMethod>, String>, Handler> routes = new HashMap();

  public static void addRoute(Enum<RequestMethod> requestMethod, String uri, Handler handler) {
    routes.put(new Tuple<>(requestMethod, uri), handler);
  }

  public static Response generateHttpResponse(BufferedReader inputStream) throws Exception {
    Request request = null;
    RequestParser requestParser = new RequestParser(inputStream);
    try {
      request = requestParser.parse();
    } catch (Exception e) {
      ErrorHandler errorHandler = new ErrorHandler(400);
      return errorHandler.generate(request);
    }
    Handler handler = retrieveHandler(request.getRequestMethod(), request.getUri());
    if (handler == null) {
      handler = clientError(request);
    }
    return handler.generate(request);
  }

  private static Handler retrieveHandler(Enum<RequestMethod> requestMethod, String uri){
    if (uri.contains("?")) {
      String[] uriParts = uri.split("\\?");
      return routes.get(new Tuple<>(requestMethod, uriParts[0]));
    } else {
      return routes.get(new Tuple<>(requestMethod, uri));
    }
  }

  private static Handler clientError(Request request){
    if (uriExists(request.getUri())){
      return new ErrorHandler(405);
    } else {
      return new ErrorHandler(404);
    }
  }

  private static boolean uriExists(String uri){
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
