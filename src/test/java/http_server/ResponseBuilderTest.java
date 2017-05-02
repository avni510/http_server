package http_server;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ResponseBuilderTest {

  @Test
  public void responseIsReturnedWithHeaderAndBody() {
    Map<String, String> headers = new HashMap();
    headers.put("Content-Type", "text/plain");
    Response response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeaders(headers)
        .setBody("hello world")
        .build();

    String expectedHttpResponse = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/plain\r\n\r\n"+ "hello world";
    assertEquals(response.getHttpResponse(), expectedHttpResponse);
  }

  @Test
  public void responseIsReturnedWithNoHeaderAndBody() {
    Response response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .build();

    String expectedHttpResponse = "HTTP/1.1 200 OK\r\n\r\n";
    assertEquals(response.getHttpResponse(), expectedHttpResponse);
  }
}
