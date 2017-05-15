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
    Response actualResponse = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeaders(headers)
        .setBody("hello world")
        .build();

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("Content-Type: text/plain\r\n", actualResponse.getHeaders());
    assertEquals("hello world", new String(actualResponse.getBody()));
  }

  @Test
  public void responseIsReturnedWithNoHeaderAndBody() {
    Response actualResponse = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .build();

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}
