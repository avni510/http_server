package http_server.response;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class ResponseTest {

  @Test
  public void httpResponseIstReturnedWithHeaderAndBody() {
    Response response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeader("Content-Type", "text/plain")
        .setBody("hello world")
        .build();

    byte[] expectedHttpResponse = ("HTTP/1.1 200 OK\r\n" + "Content-Type: text/plain\r\n\r\n"+ "hello world").getBytes();
    assertTrue(Arrays.equals(expectedHttpResponse, response.getHttpResponseBytes()));
  }

  @Test
  public void httpResponseIstReturnedWithNoHeaderAndBody() {
    Response response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(404)
        .build();

    byte[] expectedHttpResponse = ("HTTP/1.1 404 Not Found\r\n\r\n").getBytes();

    assertTrue(Arrays.equals(expectedHttpResponse, response.getHttpResponseBytes()));
  }

  @Test
  public void httpResponseIstReturnedWithHeaderButNoBody() {
    Response response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(302)
        .setHeader("Location", "http://localhost:4444/")
        .build();

    byte[] expectedHttpResponse = ("HTTP/1.1 302 Found\r\nLocation: http://localhost:4444/\r\n\r\n").getBytes();

    assertTrue(Arrays.equals(expectedHttpResponse, response.getHttpResponseBytes()));
  }
}