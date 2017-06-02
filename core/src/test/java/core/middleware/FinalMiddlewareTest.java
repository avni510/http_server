package core.middleware;

import core.middleware.FinalMiddleware;
import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

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