package http_server;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ResponseBuilderNewTest {

  @Test
  public void responseIsReturnedWithInstanceVariablesSet() {
    Map<String, String> headers = new HashMap();
    headers.put("Content-Type", "text/plain");
    Response response = new ResponseBuilderNew()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeaders(headers)
        .setbody("hello world")
        .build();

    String expectedHttpResponse = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/plain\r\n"+ "hello world\r\n\r\n";
    assertEquals(response.getHttpResponse(), expectedHttpResponse);
  }
}
