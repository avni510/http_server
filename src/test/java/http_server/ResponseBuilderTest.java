package http_server;

import org.junit.Before;
import org.junit.Test;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ResponseBuilderTest {
  private ResponseBuilder responseBuilder;

  @Before
  public void setup() {
    responseBuilder = new ResponseBuilder();
  }

  private byte[] createPlainTextResponse(String body) throws UnsupportedEncodingException {
    String expectedResponse = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/plain\r\n\r\n"+ body;
    return expectedResponse.getBytes("UTF-8");
  }

  private byte[] createHtmlTextResponse(String body) throws UnsupportedEncodingException {
    String expectedResponse = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n\r\n" + body;
    return expectedResponse.getBytes("UTF-8");
  }

  private Map<String, String> populateOneHeader(String key, String value) {
    Map<String, String> headers = new HashMap<>();
    headers.put(key, value);
    return headers;
  }

  @Test
  public void testPlainTextResponseIsReturned() throws UnsupportedEncodingException {
    String body = "hello world";
    byte[] expectedResponseBytes = createPlainTextResponse(body);

    Map <String, String> header = populateOneHeader("Content-Type", "text/plain");
    byte[] actualResponseBytes = responseBuilder.run(200, header, body);

    assertTrue(Arrays.equals(expectedResponseBytes, actualResponseBytes));
  }

  @Test
  public void testHtmlTextResponseIsReturned() throws UnsupportedEncodingException {
    String body = "<!doctype html><html><head><title> <p> Hello World </p>";
    byte[] expectedResponseBytes = createHtmlTextResponse(body);

    Map <String, String> header = populateOneHeader("Content-Type", "text/html");
    byte[] actualResponseBytes = responseBuilder.run(200, header, body);

    assertEquals(true, Arrays.equals(expectedResponseBytes, actualResponseBytes));
  }

  @Test
  public void test404ErrorIsReturned() throws UnsupportedEncodingException {
   byte[] expectedResponseBytes = "HTTP/1.1 404 Not Found\r\n\r\n".getBytes();

   byte[] actualResponseBytes = responseBuilder.run(404);

   assertEquals(true, Arrays.equals(expectedResponseBytes, actualResponseBytes));
  }
}