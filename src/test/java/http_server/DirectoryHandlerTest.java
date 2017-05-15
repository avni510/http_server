package http_server;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class DirectoryHandlerTest {

  public String getBody() {
   String body =
       "<li> <a href=/result.txt>result.txt</a></li>" +
       "<li> <a href=/validation.txt>validation.txt</a></li>" +
       "<li> <a href=/log_time_entry.txt>log_time_entry.txt</a></li>";
   return body;
  }

  @Test
  public void responseIsReturnedForGetRequest() throws UnsupportedEncodingException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .build();
    String rootDirectory = System.getProperty("user.dir") + "/code";
    DirectoryHandler directoryResponse = new DirectoryHandler(rootDirectory);

    Response actualResponse = directoryResponse.generate(request);

    assertEquals("Content-Type: text/html\r\n", actualResponse.getHeaders());
    assertEquals(getBody(), new String (actualResponse.getBody()));
  }

  @Test
  public void responseIsReturnedForHeadRequest() throws UnsupportedEncodingException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.HEAD)
        .setUri("/")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .build();
    String rootDirectory = System.getProperty("user.dir") + "/code";
    DirectoryHandler directoryResponse = new DirectoryHandler(rootDirectory);

    Response actualResponse = directoryResponse.generate(request);

    String expectedResponse = "HTTP/1.1 200 OK\r\n\r\n";
    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}