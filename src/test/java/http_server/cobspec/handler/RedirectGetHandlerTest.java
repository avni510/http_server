package http_server.cobspec.handler;

import http_server.core.Constants;

import http_server.core.response.Response;

import http_server.core.request.Request;
import http_server.core.request.RequestBuilder;
import http_server.core.request.RequestMethod;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

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
    assertEquals("Location: /" + Constants.CLRF, actualResponse.getHeaders());
  }
}