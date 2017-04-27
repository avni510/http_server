package http_server;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class DirectoryResponseTest {

  public String getBody() {
   String body =
       "<li> <a href=/code/result.txt>" +
           "result.txt</a></li>" +
       "<li> <a href=/code/validation.txt>" +
           "validation.txt</a></li>" +
       "<li> <a href=/code/log_time_entry.txt>" +
           "log_time_entry.txt</a></li>";
   return body;
  }

  @Test
  public void testResponseIsReturned() throws UnsupportedEncodingException {
    String rootDirectory = System.getProperty("user.dir") + "/code";
    DirectoryResponse directoryResponse = new DirectoryResponse(rootDirectory);

    String actualResponse = directoryResponse.generate();

    String expectedResponse = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n\r\n" + getBody();
    assertEquals(expectedResponse, actualResponse);
  }
}