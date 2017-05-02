package http_server;

import org.junit.Test;

import static org.junit.Assert.*;

public class ErrorHandlerTest {

  @Test
  public void testResponseIsReturnedFor404Error() {
    ErrorHandler errorHandler = new ErrorHandler(404);

    String actualResponse = errorHandler.generate();

    String expectedResponse = "HTTP/1.1 404 Not Found\r\n\r\n";
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void testResponseIsReturnedFor400Error() {
    ErrorHandler errorHandler = new ErrorHandler(400);

    String actualResponse = errorHandler.generate();

    String expectedResponse = "HTTP/1.1 400 Bad Request\r\n\r\n";
    assertEquals(expectedResponse, actualResponse);
  }
}