package http_server;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class HeaderTest {

  @Test
  public void testOneHeaderStringToMap() {
    Header header = new Header();
    String headerMessage =  "Host: localhost\r\n";

    Map<String, String> actualResult = header.getAllHeaders(headerMessage);

    Map<String, String> expectedResult= new HashMap<>();
    expectedResult.put("Host", "localhost");
    assertTrue(actualResult.equals(expectedResult));
  }

  @Test
  public void testMoreThanOneHeadersStringToMap() {
    Header header = new Header();
    String headerMessage =  "Host: localhost\r\n" +
                            "From: someuser@jmarshall.com\r\n" +
                            "User-Agent: HTTPTool/1.0\r\n";

    Map<String, String> actualResult = header.getAllHeaders(headerMessage);

    Map<String, String> expectedResult= new HashMap<>();
    expectedResult.put("Host", "localhost");
    expectedResult.put("From", "someuser@jmarshall.com");
    expectedResult.put("User-Agent", "HTTPTool/1.0");
    assertTrue(actualResult.equals(expectedResult));
  }

  @Test
  public void testOneHeaderInMaptoString() {
    Header header = new Header();
    Map<String, String> headerComponents= new HashMap<>();
    headerComponents.put("Host", "localhost");

    String actualResult = header.createHttpHeader(headerComponents);

    String expectedResult =  "Host: localhost\r\n";
    assertTrue(actualResult.equals(expectedResult));
  }

  @Test
  public void moreThanOneHeaderInMaptoString() {
    Header header = new Header();
    Map<String, String> headerComponents = new HashMap<>();
    headerComponents.put("Host", "localhost");
    headerComponents.put("From", "someuser@jmarshall.com");
    headerComponents.put("User-Agent", "HTTPTool/1.0");

    String actualResult = header.createHttpHeader(headerComponents);

    String expectedResult = "User-Agent: HTTPTool/1.0\r\n" +
                            "Host: localhost\r\n" +
                            "From: someuser@jmarshall.com\r\n";
    assertTrue(actualResult.equals(expectedResult));
  }
}