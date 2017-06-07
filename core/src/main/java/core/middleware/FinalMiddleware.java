package core.middleware;

import core.Handler;
import core.HttpCodes;
import core.Middleware;

import core.request.Request;

import core.response.Response;

import core.handler.ErrorHandler;

public class FinalMiddleware implements Middleware {

  public Handler call(Request request) throws Exception {
    return new ErrorHandler(HttpCodes.NOT_FOUND);
  }
}
