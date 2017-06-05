package core;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ErrorHandlerTest {

  @Test
  public void testResponseIsReturnedFor404Error() {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/code/invalid.txt")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    ErrorHandler errorHandler = new ErrorHandler(404);

    Response actualResponse = errorHandler.generate(request);

    assertEquals("404 Not Found", actualResponse.getStatusCodeMessage());
  }

  @Test
  public void testResponseIsReturnedFor400Error() {
    Request request = null;
    ErrorHandler errorHandler = new ErrorHandler(400);

    Response actualResponse = errorHandler.generate(request);

    assertEquals("400 Bad Request", actualResponse.getStatusCodeMessage());
  }
}