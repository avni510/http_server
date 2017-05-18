package http_server;

import org.junit.Test;

import static org.junit.Assert.*;

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