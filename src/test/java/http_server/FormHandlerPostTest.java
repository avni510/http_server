package http_server;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class FormHandlerPostTest {

  @Test
  public void aPostRequestIsHandled() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.POST)
        .setUri("/form")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .setBody("data=fatcat")
        .build();
    FormHandlerPost formHandlerPost = new FormHandlerPost(new DataStore());

    Response actualResponse = formHandlerPost.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}