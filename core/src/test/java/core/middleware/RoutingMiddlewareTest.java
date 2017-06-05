package core.middleware;

import core.response.Response;

import core.Router;

import core.request.RequestMethod;
import core.request.Request;
import core.request.RequestBuilder;

import core.handler.BaseHandler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoutingMiddlewareTest {
//  String rootDirectoryPath = System.getProperty("user.dir") + "/code";
  private RoutingMiddleware routingMiddleware;
  private FinalMiddleware finalMiddleware;

  private void setupPreviousMiddlewares(Router router){
    finalMiddleware = new FinalMiddleware();
    routingMiddleware = new RoutingMiddleware(router, finalMiddleware);
  }

  @Test
  public void responseIsGenerated() throws Exception {
    Router router = new Router();
    router.addRoute(RequestMethod.GET, "/hello_world", new BaseHandler());
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/hello_world")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    setupPreviousMiddlewares(router);

    Response actualResponse = routingMiddleware.call(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("hello world", new String (actualResponse.getBody()));
  }

  @Test
  public void methodNotAllowedErrorGenerated() throws Exception {
    Router router = new Router();
    router.addRoute(RequestMethod.GET, "/hello_world", new BaseHandler());
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.POST)
        .setUri("/hello_world")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    setupPreviousMiddlewares(router);

    Response actualResponse = routingMiddleware.call(request);

    assertEquals("405 Method Not Allowed", actualResponse.getStatusCodeMessage());
  }

  @Test
  public void theNextMiddlewareIsCalled() throws Exception {
    Router router = new Router();
    router.addRoute(RequestMethod.GET, "/hello_world", new BaseHandler());
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/result.txt")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost\r\n")
        .build();
    setupPreviousMiddlewares(router);

    Response actualResponse = routingMiddleware.call(request);

    assertEquals("404 Not Found", actualResponse.getStatusCodeMessage());
  }
}