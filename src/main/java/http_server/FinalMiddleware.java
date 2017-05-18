package http_server;

public class FinalMiddleware implements Middleware{

  public Response call(Request request) throws Exception {
    Handler errorHandler = new ErrorHandler(404);
    return errorHandler.generate(request);
  }
}
