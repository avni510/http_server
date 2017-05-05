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

    String actualResult = optionsHandler.generate(request);

    String expectedResult = "HTTP/1.1 200 OK\r\nAllow: GET,POST,PUT,OPTIONS,HEAD\r\n\r\n";
    assertEquals(expectedResult, actualResult);
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

    String actualResult = optionsHandler.generate(request);

    String expectedResult = "HTTP/1.1 200 OK\r\n\r\n";
    assertEquals(expectedResult, actualResult);
  }
}