package http_server;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RequestBuilderTest {

  @Test
  public void requestIsReturnedWithInstanceVariablesSet(){
    Request actualResult = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/hello_world")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .setBody("data=fatcat")
        .build();

    assertEquals(RequestMethod.GET, actualResult.getRequestMethod());
    assertEquals("/hello_world", actualResult.getUri());
    assertEquals("HTTP/1.1", actualResult.getHttpVersion());
    assertEquals(new ArrayList<>(Arrays.asList("Host: localhost")), actualResult.getHeader());
    Map<String, String> expectedBody = new HashMap<>();
    expectedBody.put("data", "fatcat");
    assertTrue(expectedBody.equals(actualResult.getBody()));
  }
}