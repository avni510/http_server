package http_server.handler.methods;

import http_server.response.Response;

import http_server.request.Request;
import http_server.request.RequestBuilder;
import http_server.request.RequestMethod;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class MethodsOptionsHandlerTest {

  @Test
  public void optionsRequestIsHandled() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.OPTIONS)
        .setUri("/method_options")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    RequestMethod[] requestMethodsOnResource = new RequestMethod[]{RequestMethod.GET, RequestMethod.POST,
                                                                   RequestMethod.PUT, RequestMethod.OPTIONS,
                                                                   RequestMethod.HEAD};
    MethodsOptionsHandler methodsOptionsHandler = new MethodsOptionsHandler(requestMethodsOnResource);

    Response actualResult = methodsOptionsHandler.generate(request);

    assertEquals("Allow: GET,POST,PUT,OPTIONS,HEAD\r\n", actualResult.getHeaders());
  }
}