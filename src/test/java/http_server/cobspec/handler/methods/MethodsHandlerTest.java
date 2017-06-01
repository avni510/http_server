package http_server.cobspec.handler.methods;

import http_server.core.response.Response;

import http_server.core.request.Request;
import http_server.core.request.RequestBuilder;
import http_server.core.request.RequestMethod;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

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