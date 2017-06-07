package cobspec.handler;

import core.response.Response;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TeapotGetHandlerTest {

  @Test
  public void FourEighteenIsReturnedForCoffeeUri() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/coffee")
        .setHeader("Host: localhost")
        .build();
    TeapotGetHandler teapotGetHandler = new TeapotGetHandler();

    Response actualResponse = teapotGetHandler.generate(request);

    assertEquals("418 I'm a teapot", actualResponse.getStatusCodeMessage());
    assertEquals("I'm a teapot", new String(actualResponse.getBody()));
  }

  @Test
  public void twoHundredIsReturnedForTeaUri() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/tea")
        .setHeader("Host: localhost")
        .build();
    TeapotGetHandler teapotGetHandler = new TeapotGetHandler();

    Response actualResponse = teapotGetHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}