package http_server.core.middleware;

import http_server.core.Middleware;
import http_server.core.Handler;
import http_server.core.Router;

import http_server.core.request.Request;

import http_server.core.response.Response;

import http_server.core.ErrorHandler;

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
