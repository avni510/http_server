package http_server.middleware;

import http_server.Request;
import http_server.RequestBuilder;
import http_server.RequestMethod;
import http_server.Response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FinalMiddlewareTest {

  @Test
  public void notFoundErrorIsGenerated() throws Exception{
    FinalMiddleware finalMiddleware = new FinalMiddleware();
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/nonexistent_route")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost\r\n")
        .build();
    Response actualResponse = finalMiddleware.call(request);

    assertEquals("404 Not Found", actualResponse.getStatusCodeMessage());
  }
}