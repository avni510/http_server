package http_server;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class MethodsHandlerTest {

  @Test
  public void resourceReturns200ForValidRequests() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/method_options")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    MethodsHandler methodsHandler = new MethodsHandler();

    Response actualResult = methodsHandler.generate(request);

    assertEquals("200 OK", actualResult.getStatusCodeMessage());
  }
}