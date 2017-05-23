package http_server;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

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
    DataStore dataStore = new DataStore();

    FormPutHandler formPutHandler = new FormPutHandler(dataStore);

    Response actualResponse = formPutHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}