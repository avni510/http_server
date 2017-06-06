package core.middleware;

import core.Middleware;
import core.Handler;
import core.Router;

import core.request.Request;

import core.handler.ErrorHandler;

public class RoutingMiddleware implements Middleware {
  private Middleware app;
  private Router router;

  public RoutingMiddleware(Router router, Middleware app) {
    this.router = router;
    this.app = app;
  }

  public Handler call(Request request) throws Exception {
    Handler handler = router.retrieveHandler(request.getRequestMethod(), request.getUri());
    if (handler != null) {
      return handler;
    } else if (router.uriExists(request.getUri())) {
      return new ErrorHandler(405);
    } else {
      return app.call(request);
    }
  }
}
