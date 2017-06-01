package http_server.cobspec.handler.directory;

import http_server.core.request.Request;
import http_server.core.request.RequestBuilder;
import http_server.core.request.RequestMethod;

import http_server.core.response.Response;

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
        .setHeader("Host: localhost")
        .build();
    DirectoryHeadHandler directoryHeadHandler = new DirectoryHeadHandler();

    Response actualResponse = directoryHeadHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}