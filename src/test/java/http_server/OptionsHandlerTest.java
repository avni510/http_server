package http_server;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class OptionsHandlerTest {

  @Test
  public void optionsRequestIsHandled() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.OPTIONS)
        .setUri("/method_options")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .build();
    RequestMethod[] requestMethodsOnResource = new RequestMethod[]{RequestMethod.GET, RequestMethod.POST,
                                                                   RequestMethod.PUT, RequestMethod.OPTIONS,
                                                                   RequestMethod.HEAD};
    OptionsHandler optionsHandler = new OptionsHandler(requestMethodsOnResource);

    Response actualResult = optionsHandler.generate(request);

    assertEquals("Allow: GET,POST,PUT,OPTIONS,HEAD\r\n", actualResult.getHeaders());
  }

  @Test
  public void resourceReturns200ForValidRequests() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/method_options")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .build();
    RequestMethod[] requestMethodsOnResource = new RequestMethod[]{RequestMethod.GET, RequestMethod.POST,
                                                                   RequestMethod.PUT, RequestMethod.OPTIONS,
                                                                   RequestMethod.HEAD};
    OptionsHandler optionsHandler = new OptionsHandler(requestMethodsOnResource);

    Response actualResult = optionsHandler.generate(request);

    String expectedResult = "HTTP/1.1 200 OK\r\n\r\n";
    assertEquals("200 OK", actualResult.getStatusCodeMessage());
  }
}