package http_server;

import org.junit.Test;

import static org.junit.Assert.*;

public class RoutingMiddlewareTest {

  @Test
  public void responseIsGenerated() throws Exception {
    Router router = new Router();
    router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldHandler());
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/hello_world")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    String rootDirectoryPath = System.getProperty("user.dir") + "/code";
    FinalMiddleware finalMiddleware = new FinalMiddleware();
    FileMiddleware fileMiddleware = new FileMiddleware(rootDirectoryPath, finalMiddleware);
    RoutingMiddleware routingMiddleware = new RoutingMiddleware(router, fileMiddleware);

    Response actualResponse = routingMiddleware.call(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("hello world", new String (actualResponse.getBody()));
  }

  @Test
  public void methodNotAllowedErrorGenerated() throws Exception {
    Router router = new Router();
    router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldHandler());
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.POST)
        .setUri("/hello_world")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    String rootDirectoryPath = System.getProperty("user.dir") + "/code";
    FinalMiddleware finalMiddleware = new FinalMiddleware();
    FileMiddleware fileMiddleware = new FileMiddleware(rootDirectoryPath, finalMiddleware);
    RoutingMiddleware routingMiddleware = new RoutingMiddleware(router, fileMiddleware);

    Response actualResponse = routingMiddleware.call(request);

    assertEquals("405 Method Not Allowed", actualResponse.getStatusCodeMessage());
  }

  @Test
  public void theNextMiddlewareIsCalled() throws Exception {
    Router router = new Router();
    router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldHandler());
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/result.txt")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost\r\n")
        .build();
    String rootDirectoryPath = System.getProperty("user.dir") + "/code";
    FinalMiddleware finalMiddleware = new FinalMiddleware();
    FileMiddleware fileMiddleware = new FileMiddleware(rootDirectoryPath, finalMiddleware);
    RoutingMiddleware routingMiddleware = new RoutingMiddleware(router, fileMiddleware);

    Response actualResponse = routingMiddleware.call(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("module TimeLogger\nend\n", new String(actualResponse.getBody()));
  }
}