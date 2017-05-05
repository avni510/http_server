package http_server;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class TeapotHandlerTest {

  @Test
  public void FourEighteenIsReturnedForCoffeeUri() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/coffee")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .build();
    TeapotHandler teapotHandler = new TeapotHandler();

    String actualResponse = teapotHandler.generate(request);

    String expectedResponse = "HTTP/1.1 418 I'm a teapot\r\nContent-Type: text/plain\r\n\r\nI'm a teapot";
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void twoHundredIsReturnedForTeaUri() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/tea")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .build();
    TeapotHandler teapotHandler = new TeapotHandler();

    String actualResponse = teapotHandler.generate(request);

    String expectedResponse = "HTTP/1.1 200 OK\r\n\r\n";
    assertEquals(expectedResponse, actualResponse);
  }
}