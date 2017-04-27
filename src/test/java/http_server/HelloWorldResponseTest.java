package http_server;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class HelloWorldResponseTest {

  @Test
  public void testResponseIsReturned() throws UnsupportedEncodingException {
    HelloWorldResponse helloWorldResponse = new HelloWorldResponse();

    String actualResponse = helloWorldResponse.generate();

    String expectedResponse = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/plain\r\n\r\n"+ "hello world";
    assertEquals(expectedResponse, actualResponse);
  }
}