package http_server.request;

import http_server.Header;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RequestTest {

  @Test
  public void requestMethodIsReturned(){
    Request request = new RequestBuilder()
                                .setRequestMethod(RequestMethod.GET)
                                .setUri("/")
                                .setHttpVersion("HTTP/1.1")
                                .setHeader("Host: localhost")
                                .setBody("data=fatcat")
                                .build();

    assertEquals(RequestMethod.GET, request.getRequestMethod());
  }

  @Test
  public void uriIsReturned(){
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .setBody("data=fatcat")
        .build();

    assertEquals("/", request.getUri());
  }

  @Test
  public void httpVersionIsReturned(){
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .setBody("data=fatcat")
        .build();

    assertEquals("HTTP/1.1", request.getHttpVersion());
  }

  @Test
  public void headerIsReturned(){
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost\r\nContent-Type: text/plain")
        .setBody("hello world")
        .build();
    Header header = new Header();

    Map<String, String> actualResult = request.getHeader();

    header.add("Host", "localhost");
    header.add("Content-Type", "text/plain");
    Map<String, String> expectedResult = header.getAllHeaders();
    assertTrue(expectedResult.equals(actualResult));
  }

  @Test
  public void bodyIsReturned(){
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .setBody("data=fatcat")
        .build();

    String actualResult = request.getEntireBody();

    assertTrue("data=fatcat".equals(actualResult));
  }

  @Test
  public void bodyParameterIsReturn(){
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .setBody("data=fatcat")
        .build();

    String actualResult = request.getBodyParam("data");

    assertEquals("fatcat", actualResult);
  }

  @Test
  public void requestIsReturnedWithNoBody(){
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();

    String actualResult = request.getEntireBody();

    assertEquals(null, actualResult);
  }

  @Test
  public void noParametersAreReturnedIfNoBodyExists(){
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();

    String actualResult = request.getBodyParam("data");

    assertEquals(null, actualResult);
  }
}