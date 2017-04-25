package http_server;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class RequestBuilderTest {

  @Test
  public void requestIsReturnedWithInstanceVariablesSet(){
    Request actualResult = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .setBody("hello world")
        .build();

    assertEquals(actualResult.getRequestMethod(), RequestMethod.GET);
    assertEquals(actualResult.getUri(), "/");
    assertEquals(actualResult.getHttpVersion(),"HTTP/1.1");
    assertEquals(actualResult.getHeader(),new ArrayList<>(Arrays.asList("Host: localhost")));
    assertEquals(actualResult.getBody(),"hello world");
  }
}