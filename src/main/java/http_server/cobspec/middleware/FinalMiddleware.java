package http_server.cobspec.middleware;

import http_server.core.Handler;
import http_server.core.Middleware;

import http_server.core.request.Request;

import http_server.core.response.Response;

import http_server.core.ErrorHandler;

public class FinalMiddleware implements Middleware {

  public Response call(Request request) throws Exception {
    Handler errorHandler = new ErrorHandler(404);
    return errorHandler.generate(request);
  }
}
