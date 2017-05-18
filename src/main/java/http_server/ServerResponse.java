package http_server;

import java.io.BufferedReader;

public class ServerResponse {
  private Middleware firstMiddleware;

  public ServerResponse(Middleware firstMiddleware) {
    this.firstMiddleware = firstMiddleware;
  }

  public Response getHttpResponse(BufferedReader inputStream) throws Exception {
    RequestParser requestParser = new RequestParser(inputStream);
    Request request = null;
    try {
      request = requestParser.parse();
    } catch (Exception e) {
      ErrorHandler errorHandler = new ErrorHandler(400);
      return errorHandler.generate(request);
    }
    return firstMiddleware.call(request);
  }
}
