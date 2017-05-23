package http_server;

import http_server.handler.ErrorHandler;

public class RoutingMiddleware implements Middleware {
  private Middleware nextMiddleware;
  private Router router;

  public RoutingMiddleware(Router router, Middleware nextMiddleware) {
    this.router = router;
    this.nextMiddleware = nextMiddleware;
  }

  public Response call(Request request) throws Exception {
    Handler handler = router.retrieveHandler(request.getRequestMethod(), request.getUri());
    if (handler != null) {
      return handler.generate(request);
    } else if (router.uriExists(request.getUri())) {
      return methodNotAllowed(request);
    } else {
      return nextMiddleware.call(request);
    }
  }

  private Response methodNotAllowed(Request request) throws Exception {
    Handler errorHandler = new ErrorHandler(405);
    return errorHandler.generate(request);
  }
}
