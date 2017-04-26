package http_server;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class RequestParserTest {
  @Test
  public void requestInstanceIsReturned() throws Exception {
    InputStream inputStream = new ByteArrayInputStream(("GET / HTTP/1.1\r\nHost: localhost\r\n" +
                                                        "Content-Type: text/plain\r\nhello world\r\n\r\n")
                                                        .getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    RequestParser requestParser = new RequestParser(bufferedReader);

    Request actualResult = requestParser.parse();

    assertEquals(RequestMethod.GET, actualResult.getRequestMethod());
    assertEquals("/", actualResult.getUri());
    assertEquals( "HTTP/1.1", actualResult.getHttpVersion());
    ArrayList<String> expectedHeader = new ArrayList<>(Arrays.asList("Host: localhost", "Content-Type: text/plain"));
    assertTrue(expectedHeader.equals(actualResult.getHeader()));
    assertEquals("hello world", actualResult.getBody());
  }

  @Test
  public void requestInstanceIsReturnedWithNoBody() throws Exception {
    InputStream inputStream = new ByteArrayInputStream(("GET / HTTP/1.1\r\nHost: localhost\r\n\r\n")
        .getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    RequestParser requestParser = new RequestParser(bufferedReader);

    Request actualResult = requestParser.parse();

    assertEquals(RequestMethod.GET, actualResult.getRequestMethod());
    assertEquals("/", actualResult.getUri());
    assertEquals( "HTTP/1.1", actualResult.getHttpVersion());
    ArrayList<String> expectedHeader = new ArrayList<>(Arrays.asList("Host: localhost"));
    assertTrue(expectedHeader.equals(actualResult.getHeader()));
    assertEquals(null, actualResult.getBody());
  }

  @Test(expected = Exception.class)
  public void testExceptionThrownForNoHeader() throws Exception {
    InputStream inputStream = new ByteArrayInputStream(("GET / HTTP/1.1\r\n\r\n")
        .getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    RequestParser requestParser = new RequestParser(bufferedReader);

   requestParser.parse();
  }

  @Test(expected = Exception.class)
  public void testExceptionThrownForInvalidRequestMethod() throws Exception {
    InputStream inputStream = new ByteArrayInputStream(("INVALID / HTTP/1.1\r\n\r\n")
        .getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    RequestParser requestParser = new RequestParser(bufferedReader);

    requestParser.parse();
  }
}