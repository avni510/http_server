package http_server.middleware;

import http_server.Middleware;
import http_server.Handler;
import http_server.Router;

import http_server.request.Request;

import http_server.response.Response;

import http_server.ErrorHandler;

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
