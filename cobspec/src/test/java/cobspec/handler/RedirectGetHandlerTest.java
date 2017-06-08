package cobspec.handler;

import core.Constants;

import core.response.Response;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RedirectGetHandlerTest {

  @Test
  public void clientGivenTheRedirectionLocation() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/redirect")
        .setHeader("Host: localhost")
        .build();
    RedirectGetHandler redirectGetHandler = new RedirectGetHandler();

    Response actualResponse = redirectGetHandler.generate(request);

    assertEquals("302 Found", actualResponse.getStatusCodeMessage());
    assertEquals("Location: /" + Constants.CLRF, actualResponse.getHeaders());
  }
}