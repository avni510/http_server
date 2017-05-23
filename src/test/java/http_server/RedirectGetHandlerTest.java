package http_server;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class RedirectGetHandlerTest {

  @Test
  public void clientGivenTheRedirectionLocation() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/redirect")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    RedirectGetHandler redirectGetHandler = new RedirectGetHandler();

    Response actualResponse = redirectGetHandler.generate(request);

    assertEquals("302 Found", actualResponse.getStatusCodeMessage());
    assertEquals("Location: /\r\n", actualResponse.getHeaders());
  }
}