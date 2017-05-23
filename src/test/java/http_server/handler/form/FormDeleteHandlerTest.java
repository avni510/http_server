package http_server.handler.form;

import http_server.Request;
import http_server.Response;
import http_server.RequestBuilder;
import http_server.RequestMethod;
import http_server.DataStore;

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
    DataStore dataStore = new DataStore();
    dataStore.storeEntry("data", "fatcat");

    FormDeleteHandler formDeleteHandler = new FormDeleteHandler(dataStore);

    Response actualResponse = formDeleteHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}