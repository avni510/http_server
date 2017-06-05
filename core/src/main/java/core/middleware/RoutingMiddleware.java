package core.middleware;

import core.Middleware;
import core.Handler;
import core.Router;

import core.request.Request;

import core.response.Response;

import core.handler.ErrorHandler;

public class RoutingMiddleware implements Middleware {
  private Middleware app;
  private Router router;

  public RoutingMiddleware(Router router, Middleware app) {
    this.router = router;
    this.app = app;
  }

  public Response call(Request request) throws Exception {
    Handler handler = router.retrieveHandler(request.getRequestMethod(), request.getUri());
    if (handler != null) {
      return handler.generate(request);
    } else if (router.uriExists(request.getUri())) {
      return methodNotAllowed(request);
    } else {
      return app.call(request);
    }
  }

  private Response methodNotAllowed(Request request) throws Exception {
    Handler errorHandler = new ErrorHandler(405);
    return errorHandler.generate(request);
  }
}