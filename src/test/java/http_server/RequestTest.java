package http_server;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RequestTest {
  private Request request;

  @Before
  public void setup() {
    request = new Request("GET / HTTP/1.1\r\nHost: localhost\r\n\r\n");
  }

  @Test
  public void testRequestIsReturned() {
    assertEquals("GET", request.getRequestMethod());
  }

  @Test
  public void testUriIsReturned() {
    assertEquals("/", request.getUri());
  }

  @Test
  public void testHttpVersionIsReturned() {
    assertEquals("HTTP/1.1", request.getHttpVersion());
  }

  @Test
  public void testHostNameIsReturned() {
    assertEquals("localhost", request.getHostName());
  }
}