package http_server.handler;

import http_server.Request;
import http_server.Response;
import http_server.RequestBuilder;
import http_server.RequestMethod;
import http_server.DataStore;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FormPostHandlerTest {

  @Test
  public void aPostRequestIsHandled() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.POST)
        .setUri("/form")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .setBody("data=fatcat")
        .build();
    FormPostHandler formPostHandler = new FormPostHandler(new DataStore());

    Response actualResponse = formPostHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}