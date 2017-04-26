package http_server;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class RequestTest {

  @Test
  public void testRequestMethodIsReturned(){
    Request requestNew = new RequestBuilder()
                                .setRequestMethod(RequestMethod.GET)
                                .setUri("/")
                                .setHttpVersion("HTTP/1.1")
                                .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
                                .setBody("hello world")
                                .build();

    assertEquals(RequestMethod.GET, requestNew.getRequestMethod());
  }

  @Test
  public void testUriIsReturned(){
    Request requestNew = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .setBody("hello world")
        .build();

    assertEquals("/", requestNew.getUri());
  }

  @Test
  public void testHttpVersionIsReturned(){
    Request requestNew = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .setBody("hello world")
        .build();

    assertEquals("HTTP/1.1", requestNew.getHttpVersion());
  }

  @Test
  public void testHeaderIsReturned(){
    Request requestNew = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost", "Content-Type: text/plain")))
        .setBody("hello world")
        .build();

    ArrayList<String> actualResult = requestNew.getHeader();
    ArrayList<String> expectedResult = new ArrayList<>();
    expectedResult.add("Host: localhost");
    expectedResult.add("Content-Type: text/plain");
    assertTrue(actualResult.equals(expectedResult));
  }

  @Test
  public void testBodyIsReturned(){
    Request requestNew = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .setBody("hello world")
        .build();

    assertEquals("hello world", requestNew.getBody());
  }
}