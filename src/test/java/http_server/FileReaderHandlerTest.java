package http_server;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class FileReaderHandlerTest {

  @Test
  public void testResponseIsReturned() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/code/result.txt")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .setBody("hello world")
        .build();
    String pathToFile = System.getProperty("user.dir") + "/code/result.txt";
    FileReaderHandler fileReaderHandler = new FileReaderHandler(pathToFile);

    String actualResponse = fileReaderHandler.generate(request);

    String expectedResponse = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/plain\r\n\r\n" + "module TimeLogger\nend\n";
    assertEquals(expectedResponse, actualResponse);
  }
}
