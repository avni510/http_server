package http_server.handler.directory;

import http_server.request.Request;
import http_server.request.RequestBuilder;
import http_server.request.RequestMethod;

import http_server.response.Response;

import static org.junit.Assert.assertEquals;

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