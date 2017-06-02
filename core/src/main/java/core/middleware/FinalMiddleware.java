package core.middleware;

import core.Handler;
import core.Middleware;

import core.request.Request;

import core.response.Response;

import core.ErrorHandler;

public class FinalMiddleware implements Middleware {

  public Response call(Request request) throws Exception {
    Handler errorHandler = new ErrorHandler(404);
    return errorHandler.generate(request);
  }
}
