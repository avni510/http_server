package http_server;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class HelloWorldHandlerTest {

  @Test
  public void testResponseIsReturned() throws UnsupportedEncodingException {
    HelloWorldHandler helloWorldHandler = new HelloWorldHandler();

    String actualResponse = helloWorldHandler.generate();

    String expectedResponse = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/plain\r\n\r\n"+ "hello world";
    assertEquals(expectedResponse, actualResponse);
  }
}