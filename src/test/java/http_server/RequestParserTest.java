package http_server;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class RequestParserTest {
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
    assertEquals(null, actualResult.getEntireBody());
  }

  @Test
  public void requestInstanceIsReturned() throws Exception {
    String requestString = "POST /form HTTP/1.1\r\n" +
                           "Host: localhost\r\n" +
                           "Content-Type: application/x-www-form-urlencoded\r\n" +
                           "Content-Length: 11\r\n\r\n" +
                           "data=fatcat";
    InputStream inputStream = new ByteArrayInputStream(requestString.getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    RequestParser requestParser = new RequestParser(bufferedReader);

    Request actualResult = requestParser.parse();

    assertEquals(RequestMethod.POST, actualResult.getRequestMethod());
    assertEquals("/form", actualResult.getUri());
    assertEquals( "HTTP/1.1", actualResult.getHttpVersion());
    ArrayList<String> expectedHeader = new ArrayList<>(Arrays.asList("Host: localhost",
        "Content-Type: application/x-www-form-urlencoded", "Content-Length: 11"));
    assertTrue(expectedHeader.equals(actualResult.getHeader()));
    assertTrue("fatcat".equals(actualResult.getBodyParam("data")));
  }

  @Test(expected = Exception.class)
  public void testExceptionThrownForNoHeader() throws Exception {
    InputStream inputStream = new ByteArrayInputStream(("GET / HTTP/1.1\r\n\r\n")
        .getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    RequestParser requestParser = new RequestParser(bufferedReader);

   requestParser.parse();
  }

  @Test
  public void testInvalidRequestMethodIsSet() throws Exception {
    InputStream inputStream = new ByteArrayInputStream(("INVALID / HTTP/1.1\r\nHost: localhost\r\n").getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    RequestParser requestParser = new RequestParser(bufferedReader);

    Request actualResult = requestParser.parse();

    assertEquals(RequestMethod.INVALID_REQUEST_METHOD, actualResult.getRequestMethod());
    assertEquals("/", actualResult.getUri());
    assertEquals( "HTTP/1.1", actualResult.getHttpVersion());
  }
}