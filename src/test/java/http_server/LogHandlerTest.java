package http_server;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class LogHandlerTest {

  @Test
  public void requestIsDisplayed() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/log")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .build();
    LogHandler logHandler = new LogHandler();

    String actualResponse = logHandler.generate(request);
    String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\nGET /log HTTP/1.1";
    assertEquals(expectedResponse, actualResponse);
  }

}