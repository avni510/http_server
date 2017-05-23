package http_server;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;

public class DirectoryHeadHandlerTest {

  @Test
  public void responseIsReturnedForHeadRequest() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.HEAD)
        .setUri("/")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost\r\n")
        .build();
    DirectoryHeadHandler directoryHeadHandler = new DirectoryHeadHandler();

    Response actualResponse = directoryHeadHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}