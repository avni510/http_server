package http_server;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class Router {
  private Map<Tuple<Enum<RequestMethod>, String>, Handler> routes = new HashMap();

  public void populateRoutes(String rootDirectoryPath) {
    addRoute(RequestMethod.GET, "/", new HelloWorldHandler());
    addRoute(RequestMethod.GET, "/code", new DirectoryHandler(rootDirectoryPath));
    populateFileRoutes(rootDirectoryPath);
  }

  public void populateFileRoutes(String rootDirectoryPath){
    FileManager fileManager = new FileManager();
    Map<String, String> relativeAndAbsolutePaths = fileManager.getRelativeAndAbsolutePath(rootDirectoryPath);
    for (Map.Entry<String, String> path : relativeAndAbsolutePaths.entrySet()) {
      addRoute(RequestMethod.GET, path.getKey(), new FileReaderHandler(path.getValue()));
    }
  }

  public String generateHttpResponse(BufferedReader inputStream) throws Exception {
    Request request;
    RequestParser requestParser = new RequestParser(inputStream);
    try {
      request = requestParser.parse();
    } catch (Exception e) {
      Response response = new ResponseBuilder()
                          .setHttpVersion("HTTP/1.1")
                          .setStatusCode(400)
                          .build();
      return response.getHttpResponse();
    }
    try {
      Handler handler = retrieveHandler(request.getRequestMethod(), request.getUri());
      return handler.generate();
    } catch (Exception e) {
      Response response = new ResponseBuilder()
                          .setHttpVersion("HTTP/1.1")
                          .setStatusCode(404)
                          .build();
      return response.getHttpResponse();
    }
  }

  private Handler retrieveHandler(Enum<RequestMethod> requestMethod, String uri){
    return routes.get(new Tuple<>(requestMethod, uri));
  }

  private void addRoute(Enum<RequestMethod> requestMethod, String uri, Handler handler) {
   routes.put(new Tuple<>(requestMethod, uri), handler);
  }
}
