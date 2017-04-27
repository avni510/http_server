package http_server;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class FileReaderHandlerTest {

  @Test
  public void testResponseIsReturned() throws IOException {
    String pathToFile = System.getProperty("user.dir") + "/code/result.txt";
    FileReaderHandler fileReaderHandler = new FileReaderHandler(pathToFile);

    String actualResponse = fileReaderHandler.generate();

    String expectedResponse = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/plain\r\n\r\n" + "module TimeLogger\nend\n";
    assertEquals(expectedResponse, actualResponse);
  }
}
