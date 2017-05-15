package http_server;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class RedirectHandlerTest {

  @Test
  public void clientGivenTheRedirectionLocation() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/redirect")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .build();
    RedirectHandler redirectHandler = new RedirectHandler();

    Response actualResponse = redirectHandler.generate(request);

    assertEquals("302 Found", actualResponse.getStatusCodeMessage());
    assertEquals("Location: /\r\n", actualResponse.getHeaders());
  }
}