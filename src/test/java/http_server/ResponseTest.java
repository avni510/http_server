package http_server;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ResponseTest {

  @Test
  public void httpResponseIstReturnedWithHeaderAndBody() {
    Map<String, String> headers = new HashMap();
    headers.put("Content-Type", "text/plain");
    Response response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeaders(headers)
        .setBody("hello world")
        .build();

    String expectedHttpResponse = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/plain\r\n\r\n"+ "hello world";
    assertEquals(expectedHttpResponse, response.getHttpResponse());
  }

  @Test
  public void httpResponseIstReturnedWithNoHeaderAndBody() {
    Response response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(404)
        .setHeaders(null)
        .setBody(null)
        .build();

    String expectedHttpResponse = "HTTP/1.1 404 Not Found\r\n\r\n";
    assertEquals(expectedHttpResponse, response.getHttpResponse());
  }

  @Test
  public void httpResponseIstReturnedWithHeaderButNoBody() {
    Map<String, String> headers = new HashMap();
    headers.put("Location", "http://localhost:4444/");
    Response response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(302)
        .setHeaders(headers)
        .build();

    String expectedHttpResponse = "HTTP/1.1 302 Found\r\nLocation: http://localhost:4444/\r\n\r\n";
    assertEquals(expectedHttpResponse, response.getHttpResponse());
  }
}