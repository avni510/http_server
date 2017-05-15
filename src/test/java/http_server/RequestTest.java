package http_server;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RequestTest {

  @Test
  public void requestMethodIsReturned(){
    Request request = new RequestBuilder()
                                .setRequestMethod(RequestMethod.GET)
                                .setUri("/")
                                .setHttpVersion("HTTP/1.1")
                                .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
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
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
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
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
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
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost", "Content-Type: text/plain")))
        .setBody("hello world")
        .build();

    ArrayList<String> actualResult = request.getHeader();

    ArrayList<String> expectedResult = new ArrayList<>();
    expectedResult.add("Host: localhost");
    expectedResult.add("Content-Type: text/plain");
    assertTrue(actualResult.equals(expectedResult));
  }

  @Test
  public void bodyIsReturned(){
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
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
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
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
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
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
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .build();

    String actualResult = request.getBodyParam("data");

    assertEquals(null, actualResult);
  }
}