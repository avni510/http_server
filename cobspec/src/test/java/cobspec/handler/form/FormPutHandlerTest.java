package cobspec.handler.form;

import core.DataStore;
import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

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