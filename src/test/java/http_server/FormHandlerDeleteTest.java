package http_server;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class FormHandlerDeleteTest {

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

    FormHandlerDelete formHandlerDelete = new FormHandlerDelete(dataStore);

    Response actualResponse = formHandlerDelete.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}