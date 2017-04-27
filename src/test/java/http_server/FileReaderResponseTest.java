package http_server;

import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class FileReaderResponseTest {

  @Test
  public void testResponseIsReturned() throws IOException {
    String pathToFile = System.getProperty("user.dir") + "/code/result.txt";
    FileReaderResponse fileReaderResponse = new FileReaderResponse(pathToFile);

    String actualResponse = fileReaderResponse.generate();

    String expectedResponse = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/plain\r\n\r\n" + "module TimeLogger\nend\n";
    assertEquals(expectedResponse, actualResponse);
  }
}
