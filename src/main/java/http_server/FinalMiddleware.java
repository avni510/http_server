package http_server;

import http_server.handler.ErrorHandler;

public class FinalMiddleware implements Middleware{

  public Response call(Request request) throws Exception {
    Handler errorHandler = new ErrorHandler(404);
    return errorHandler.generate(request);
  }
}
