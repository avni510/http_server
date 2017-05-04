package http_server;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ErrorHandlerTest {

  @Test
  public void testResponseIsReturnedFor404Error() {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/code/invalid.txt")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .build();
    ErrorHandler errorHandler = new ErrorHandler(404);

    String actualResponse = errorHandler.generate(request);

    String expectedResponse = "HTTP/1.1 404 Not Found\r\n\r\n";
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void testResponseIsReturnedFor400Error() {
    Request request = null;
    ErrorHandler errorHandler = new ErrorHandler(400);

    String actualResponse = errorHandler.generate(request);

    String expectedResponse = "HTTP/1.1 400 Bad Request\r\n\r\n";
    assertEquals(expectedResponse, actualResponse);
  }
}