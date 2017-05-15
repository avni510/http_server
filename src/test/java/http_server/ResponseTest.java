package http_server;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
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
    Map<String, String> headers = new HashMap();
    headers.put("Location", "http://localhost:4444/");
    Response response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(302)
        .setHeaders(headers)
        .build();

    byte[] expectedHttpResponse = ("HTTP/1.1 302 Found\r\nLocation: http://localhost:4444/\r\n\r\n").getBytes();

    assertTrue(Arrays.equals(expectedHttpResponse, response.getHttpResponseBytes()));
  }
}