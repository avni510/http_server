package core.request;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestBuilderTest {

  @Test
  public void requestIsReturnedWithInstanceVariablesSet(){
    Header header = new Header();
    Request actualResult = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/hello_world")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .setBody("data=fatcat")
        .build();

    assertEquals(RequestMethod.GET, actualResult.getRequestMethod());
    assertEquals("/hello_world", actualResult.getUri());
    assertEquals("HTTP/1.1", actualResult.getHttpVersion());
    header.add("Host", "localhost");
    assertEquals(header.getAllHeaders(), actualResult.getHeader());
    assertEquals("fatcat", actualResult.getBodyParam("data"));
  }
}