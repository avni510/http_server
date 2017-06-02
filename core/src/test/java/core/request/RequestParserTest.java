package core.request;

import core.Constants;
import core.Header;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

public class RequestParserTest {
  @Test
  public void requestInstanceIsReturnedWithNoBody() throws Exception {
    InputStream inputStream = new ByteArrayInputStream(("GET / HTTP/1.1" + Constants.CLRF +
                                                        "Host: localhost" + Constants.CLRF + Constants.CLRF).getBytes());
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
    String requestString = "POST /form HTTP/1.1" + Constants.CLRF +
                           "Host: localhost" + Constants.CLRF +
                           "Content-Type: application/x-www-form-urlencoded" + Constants.CLRF +
                           "Content-Length: 11" + Constants.CLRF + Constants.CLRF +
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
    InputStream inputStream = new ByteArrayInputStream(("GET / HTTP/1.1" + Constants.CLRF + Constants.CLRF).getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    RequestParser requestParser = new RequestParser(bufferedReader);

   requestParser.parse();
  }

  @Test
  public void testInvalidRequestMethodIsSet() throws Exception {
    InputStream inputStream = new ByteArrayInputStream(("INVALID / HTTP/1.1" + Constants.CLRF +
                                                        "Host: localhost" + Constants.CLRF).getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    RequestParser requestParser = new RequestParser(bufferedReader);

    Request actualResult = requestParser.parse();

    assertEquals(RequestMethod.UNSUPPORTED, actualResult.getRequestMethod());
    assertEquals("/", actualResult.getUri());
    assertEquals( "HTTP/1.1", actualResult.getHttpVersion());
  }
}