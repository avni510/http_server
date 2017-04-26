package http_server;

import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class FileReaderResponseTest {

  @Test
  public void testResponseIsReturned() throws IOException {
    FileReaderResponse fileReaderResponse = new FileReaderResponse("/Users/avnikothari/Desktop/resident_apprenticeship/java/http_server/code/result.txt");
    String expectedResponse = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/plain\r\n\r\n" + "module TimeLogger\nend\n";
    byte[] expectedResponseBytes = expectedResponse.getBytes("UTF-8");
    assertTrue(Arrays.equals(expectedResponseBytes, fileReaderResponse.generate()));
  }
}
