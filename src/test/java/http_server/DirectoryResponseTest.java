package http_server;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class DirectoryResponseTest {

  public String getBody() {
   String body =
       "<li> <a href=/Users/avnikothari/Desktop/resident_apprenticeship/java/http_server/code/result.txt>" +
           "result.txt</a></li>" +
       "<li> <a href=/Users/avnikothari/Desktop/resident_apprenticeship/java/http_server/code/validation.txt>" +
           "validation.txt</a></li>" +
       "<li> <a href=/Users/avnikothari/Desktop/resident_apprenticeship/java/http_server/code/log_time_entry.txt>" +
           "log_time_entry.txt</a></li>";
   return body;
  }

  @Test
  public void testResponseIsReturned() throws UnsupportedEncodingException {
   DirectoryResponse directoryResponse = new DirectoryResponse("/Users/avnikothari/Desktop/resident_apprenticeship/java/http_server/code/");
   byte[] expectedResponse = ("HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n\r\n"+ getBody()).getBytes();

   byte[] actualResponse = directoryResponse.generate();

   assertEquals(true, Arrays.equals(expectedResponse, actualResponse));
  }
}