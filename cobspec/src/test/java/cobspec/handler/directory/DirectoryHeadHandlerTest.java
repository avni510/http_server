package cobspec.handler.directory;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

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