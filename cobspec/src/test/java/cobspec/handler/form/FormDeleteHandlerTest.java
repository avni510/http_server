package cobspec.handler.form;

import core.utils.DataStore;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FormDeleteHandlerTest {

  @Test
  public void aDeleteRequestIsHandled() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.DELETE)
        .setUri("/form")
        .setHeader("Host: localhost")
        .build();
    DataStore<String, String> dataStore = new DataStore<>();
    dataStore.storeEntry("data", "fatcat");

    FormDeleteHandler formDeleteHandler = new FormDeleteHandler(dataStore);

    Response actualResponse = formDeleteHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}