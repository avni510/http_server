package http_server.cobspec.handler.form;

import http_server.DataStore;
import http_server.request.Request;
import http_server.request.RequestBuilder;
import http_server.request.RequestMethod;

import http_server.response.Response;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FormDeleteHandlerTest {

  @Test
  public void aDeleteRequestIsHandled() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.DELETE)
        .setUri("/form")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    DataStore<String, String> dataStore = new DataStore<>();
    dataStore.storeEntry("data", "fatcat");

    FormDeleteHandler formDeleteHandler = new FormDeleteHandler(dataStore);

    Response actualResponse = formDeleteHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}