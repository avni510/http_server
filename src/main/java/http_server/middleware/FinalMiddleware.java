package http_server.middleware;

import http_server.Handler;
import http_server.Middleware;
import http_server.request.Request;
import http_server.response.Response;

import http_server.handler.ErrorHandler;

public class FinalMiddleware implements Middleware {

  public Response call(Request request) throws Exception {
    Handler errorHandler = new ErrorHandler(404);
    return errorHandler.generate(request);
  }
}
