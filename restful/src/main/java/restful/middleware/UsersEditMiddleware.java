package restful.middleware;

import core.Handler;
import core.Middleware;

import core.request.Request;

import restful.handler.users.UsersNewHandler;

public class UsersEditMiddleware implements Middleware{
  private Middleware app;

  public UsersEditMiddleware(Middleware app) {
    this.app = app;
  }

  public Handler call(Request request) throws Exception {
    if(request.getUri().contains("edit")) {
      return new UsersNewHandler();
    } else {
      return app.call(request);
    }
  }
}
