package http_server.cobspec.handler.form;

import http_server.DataStore;
import http_server.request.Request;
import http_server.request.RequestBuilder;
import http_server.request.RequestMethod;

import http_server.response.Response;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FormPutHandlerTest {

  @Test
  public void aPutRequestIsHandled() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.PUT)
        .setUri("/form")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .setBody("data=heathcliff")
        .build();
    DataStore<String, String> dataStore = new DataStore<String, String>();

    FormPutHandler formPutHandler = new FormPutHandler(dataStore);

    Response actualResponse = formPutHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}