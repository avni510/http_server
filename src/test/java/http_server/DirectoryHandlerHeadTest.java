package http_server;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;

public class DirectoryHandlerHeadTest {

  @Test
  public void responseIsReturnedForHeadRequest() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.HEAD)
        .setUri("/")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost\r\n")
        .build();
    DirectoryHandlerHead directoryHandlerHead = new DirectoryHandlerHead();

    Response actualResponse = directoryHandlerHead.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}