package http_server;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class DirectoryHandlerGetTest {

  public String getBody() {
   String body =
       "<li> <a href=/image.png>image.png</a></li>" +
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
        .setHeader("Host: localhost\r\n")
        .build();
    String rootDirectory = System.getProperty("user.dir") + "/code";
    DirectoryHandlerGet directoryHandlerGet = new DirectoryHandlerGet(rootDirectory);

    Response actualResponse = directoryHandlerGet.generate(request);

    assertEquals("Content-Type: text/html\r\n", actualResponse.getHeaders());
    assertEquals(getBody(), new String (actualResponse.getBody()));
  }
}