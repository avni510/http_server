package http_server.response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseBuilderTest {

  @Test
  public void responseIsReturnedWithHeaderAndBody() {
    Response actualResponse = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeader("Set-Cookie", "foobar")
        .setHeader("Content-Type", "text/plain")
        .setBody("hello world")
        .build();

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("Set-Cookie: foobar\r\nContent-Type: text/plain\r\n", actualResponse.getHeaders());
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
