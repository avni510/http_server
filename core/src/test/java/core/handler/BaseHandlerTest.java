package core.handler;

import core.response.Response;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class BaseHandlerTest {

  @Test
  public void testResponseIsReturned() throws UnsupportedEncodingException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/hello_world")
        .setHeader("Host: localhost")
        .build();
    BaseHandler baseHandler = new BaseHandler();

    Response actualResponse = baseHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("hello world", new String(actualResponse.getBody()));
  }
}