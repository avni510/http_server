package http_server.cobspec.middleware;

import http_server.Middleware;

import http_server.request.Request;
import http_server.request.RequestBuilder;
import http_server.request.RequestMethod;

import http_server.response.Response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileMiddlewareTest {

  @Test
  public void responseForFileIsReturned() throws Exception{
    String rootDirectoryPath = System.getProperty("user.dir") + "/code";
    Middleware nextMiddleware = new FinalMiddleware();
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/result.txt")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    FileMiddleware fileMiddleware = new FileMiddleware(rootDirectoryPath, nextMiddleware);

    Response actualResponse = fileMiddleware.call(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("module TimeLogger\nend\n", new String(actualResponse.getBody()));
  }

  @Test
  public void responseForFileIsCreatedAsNeeded() throws Exception {
    String rootDirectoryPath = System.getProperty("user.dir") + "/code";
    Middleware nextMiddleware = new FinalMiddleware();
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/validation.txt")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    FileMiddleware fileMiddleware = new FileMiddleware(rootDirectoryPath, nextMiddleware);

    Response actualResponse = fileMiddleware.call(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("x = 1\ny = 2\n", new String(actualResponse.getBody()));
  }

  @Test
  public void methodNotAllowedErrorIsReturned() throws Exception {
    String rootDirectoryPath = System.getProperty("user.dir") + "/code";
    Middleware nextMiddleware = new FinalMiddleware();
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.PUT)
        .setUri("/validation.txt")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    FileMiddleware fileMiddleware = new FileMiddleware(rootDirectoryPath, nextMiddleware);

    Response actualResponse = fileMiddleware.call(request);

    assertEquals("405 Method Not Allowed", actualResponse.getStatusCodeMessage());
  }

  @Test
  public void nextMiddlewareIsCalled() throws Exception {
    String rootDirectoryPath = System.getProperty("user.dir") + "/code";
    Middleware nextMiddleware = new FinalMiddleware();
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/nonexistent_route")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    FileMiddleware fileMiddleware = new FileMiddleware(rootDirectoryPath, nextMiddleware);

    Response actualResponse = fileMiddleware.call(request);

    assertEquals("404 Not Found", actualResponse.getStatusCodeMessage());
  }
}