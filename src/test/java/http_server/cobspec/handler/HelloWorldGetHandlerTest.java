package http_server.cobspec.handler;

import http_server.core.response.Response;

import http_server.core.request.Request;
import http_server.core.request.RequestBuilder;
import http_server.core.request.RequestMethod;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class HelloWorldGetHandlerTest {

  @Test
  public void testResponseIsReturned() throws UnsupportedEncodingException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/hello_world")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    HelloWorldGetHandler helloWorldGetHandler = new HelloWorldGetHandler();

    Response actualResponse = helloWorldGetHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("hello world", new String (actualResponse.getBody()));
  }
}