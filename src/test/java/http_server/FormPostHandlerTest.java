package http_server;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

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