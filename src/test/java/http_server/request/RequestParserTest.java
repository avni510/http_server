package http_server.request;

import http_server.Header;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

public class RequestParserTest {
  @Test
  public void requestInstanceIsReturnedWithNoBody() throws Exception {
    InputStream inputStream = new ByteArrayInputStream(("GET / HTTP/1.1\r\nHost: localhost\r\n\r\n")
        .getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    Header header = new Header();
    RequestParser requestParser = new RequestParser(bufferedReader);

    Request actualResult = requestParser.parse();

    assertEquals(RequestMethod.GET, actualResult.getRequestMethod());
    assertEquals("/", actualResult.getUri());
    assertEquals( "HTTP/1.1", actualResult.getHttpVersion());
    header.add("Host", "localhost");
    assertEquals(header.getAllHeaders(), actualResult.getHeader());
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
    Header header = new Header();
    RequestParser requestParser = new RequestParser(bufferedReader);

    Request actualResult = requestParser.parse();

    assertEquals(RequestMethod.POST, actualResult.getRequestMethod());
    assertEquals("/form", actualResult.getUri());
    assertEquals( "HTTP/1.1", actualResult.getHttpVersion());
    header.add("Host", "localhost");
    header.add("Content-Type", "application/x-www-form-urlencoded");
    header.add("Content-Length", "11");
    assertEquals(header.getAllHeaders(), actualResult.getHeader());
    assertEquals("fatcat", actualResult.getBodyParam("data"));
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