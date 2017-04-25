package http_server;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class RequestParserTest {

  @Test
  public void requestInstanceIsReturned() throws IOException {
    InputStream inputStream = new ByteArrayInputStream(("GET / HTTP/1.1\r\nHost: localhost\r\n" +
                                                        "Content-Type: text/plain\r\nhello world\r\n\r\n")
                                                        .getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    RequestParser requestParser = new RequestParser(bufferedReader);

    Request actualResult = requestParser.parse();

    assertEquals(actualResult.getRequestMethod(), RequestMethod.GET);
    assertEquals(actualResult.getUri(), "/");
    assertEquals(actualResult.getHttpVersion(), "HTTP/1.1");
    ArrayList<String> expectedHeader = new ArrayList<>(Arrays.asList("Host: localhost", "Content-Type: text/plain"));
    assertTrue(actualResult.getHeader().equals(expectedHeader));
    assertEquals(actualResult.getBody(), "hello world");
  }
}