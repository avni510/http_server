package http_server.cobspec.handler.form;

import http_server.DataStore;
import http_server.request.Request;
import http_server.request.RequestBuilder;
import http_server.request.RequestMethod;

import http_server.response.Response;

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
    FormPostHandler formPostHandler = new FormPostHandler(new DataStore<String, String>());

    Response actualResponse = formPostHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}