package http_server;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class Router {
  private static Map<Tuple<Enum<RequestMethod>, String>, Handler> routes = new HashMap();

  public static void addRoute(Enum<RequestMethod> requestMethod, String uri, Handler handler) {
    routes.put(new Tuple<>(requestMethod, uri), handler);
  }

  public static String generateHttpResponse(BufferedReader inputStream) throws Exception {
    Request request = null;
    RequestParser requestParser = new RequestParser(inputStream);
    try {
      request = requestParser.parse();
    } catch (Exception e) {
      ErrorHandler errorHandler = new ErrorHandler(400);
      return errorHandler.generate(request);
    }
    try {
      Handler handler = retrieveHandler(request.getRequestMethod(), request.getUri());
      return handler.generate(request);
    } catch (Exception e) {
      ErrorHandler errorHandler = new ErrorHandler(404);
      return errorHandler.generate(request);
    }
  }

  private static Handler retrieveHandler(Enum<RequestMethod> requestMethod, String uri){
    return routes.get(new Tuple<>(requestMethod, uri));
  }
}
