package http_server.cobspec.middleware;

import http_server.cobspec.middleware.FinalMiddleware;
import http_server.core.request.Request;
import http_server.core.request.RequestBuilder;
import http_server.core.request.RequestMethod;

import http_server.core.response.Response;

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
        .setHeader("Host: localhost")
        .build();
    Response actualResponse = finalMiddleware.call(request);

    assertEquals("404 Not Found", actualResponse.getStatusCodeMessage());
  }
}