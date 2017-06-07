package core.middleware;

import core.Handler;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FinalMiddlewareTest {

  @Test
  public void notFoundErrorIsGenerated() throws Exception {
    FinalMiddleware finalMiddleware = new FinalMiddleware();
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/nonexistent_route")
        .setHeader("Host: localhost")
        .build();

    Handler handler = finalMiddleware.call(request);

    Response actualResponse = handler.generate(request);
    assertEquals("404 Not Found", actualResponse.getStatusCodeMessage());
  }
}