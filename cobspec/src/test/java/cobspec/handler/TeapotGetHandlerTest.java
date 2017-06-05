package cobspec.handler;

import core.response.Response;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TeapotGetHandlerTest {

  @Test
  public void FourEighteenIsReturnedForCoffeeUri() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/coffee")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    TeapotGetHandler teapotGetHandler = new TeapotGetHandler();

    Response actualResponse = teapotGetHandler.generate(request);

    assertEquals("418 I'm a teapot", actualResponse.getStatusCodeMessage());
    assertEquals("I'm a teapot", new String (actualResponse.getBody()));
  }

  @Test
  public void twoHundredIsReturnedForTeaUri() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/tea")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    TeapotGetHandler teapotGetHandler = new TeapotGetHandler();

    Response actualResponse = teapotGetHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}