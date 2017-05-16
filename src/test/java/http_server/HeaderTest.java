package http_server;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class HeaderTest {

  @Test
  public void headersAreAddedToAMap(){
    Header header = new Header();

    header.add("Host", "localhost");
    Map<String, String> actualResult = header.getAllHeaders();
    Map<String, String> expectedResult= new HashMap<>();
    expectedResult.put("Host", "localhost");
    assertTrue(expectedResult.equals(actualResult));
  }

  @Test
  public void headersAreConvertedToAMap() {
    String headerMessage =  "Host: localhost\r\n" +
                            "From: someuser@jmarshall.com\r\n" +
                            "User-Agent: HTTPTool/1.0\r\n" +
                            "Authorization: Basic YWRtaW46aHVudGVyMg==\r\n";
    Header header = new Header();

    header.populate(headerMessage);
    Map<String, String> actualResult = header.getAllHeaders();

    Map<String, String> expectedResult= new HashMap<>();
    expectedResult.put("Host", "localhost");
    expectedResult.put("From", "someuser@jmarshall.com");
    expectedResult.put("User-Agent", "HTTPTool/1.0");
    expectedResult.put("Authorization", "Basic YWRtaW46aHVudGVyMg==");
    assertTrue(expectedResult.equals(actualResult));
  }

  @Test
  public void headerMapsAreConvertedToString() {
    Header header = new Header();
    header.add("Host", "localhost");
    header.add("From", "someuser@jmarshall.com");
    header.add("User-Agent", "HTTPTool/1.0");
    String actualResult = header.convertHeadersToString();

    String expectedResult = "User-Agent: HTTPTool/1.0\r\n" +
                            "Host: localhost\r\n" +
                            "From: someuser@jmarshall.com\r\n";
    assertTrue(expectedResult.equals(actualResult));
  }

  @Test
  public void headerValueIsReturned() {
    Header header = new Header();
    header.add("Host", "localhost");

    String actualResult = header.getValue("Host");

    assertTrue("localhost".equals(actualResult));
  }
}