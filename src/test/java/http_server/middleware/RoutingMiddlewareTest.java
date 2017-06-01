package http_server.middleware;

import http_server.cobspec.middleware.FileMiddleware;
import http_server.cobspec.middleware.FinalMiddleware;
import http_server.response.Response;

import http_server.Router;

import http_server.request.RequestMethod;
import http_server.request.Request;
import http_server.request.RequestBuilder;

import http_server.cobspec.handler.HelloWorldGetHandler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoutingMiddlewareTest {
  String rootDirectoryPath = System.getProperty("user.dir") + "/code";
  private RoutingMiddleware routingMiddleware;
  private FileMiddleware fileMiddleware;
  private FinalMiddleware finalMiddleware;

  private void setupPreviousMiddlewares(Router router){
    finalMiddleware = new FinalMiddleware();
    fileMiddleware = new FileMiddleware(rootDirectoryPath, finalMiddleware);
    routingMiddleware = new RoutingMiddleware(router, fileMiddleware);
  }

  @Test
  public void responseIsGenerated() throws Exception {
    Router router = new Router();
    router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldGetHandler());
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
    router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldGetHandler());
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
    router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldGetHandler());
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/result.txt")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost\r\n")
        .build();
    setupPreviousMiddlewares(router);

    Response actualResponse = routingMiddleware.call(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("module TimeLogger\nend\n", new String(actualResponse.getBody()));
  }
}