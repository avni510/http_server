package http_server;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CookieGetHandlerTest {

  @Test
  public void cookieIsSentBack() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/cookie?type=chocolate")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost\r\n")
        .build();
    CookieGetHandler cookieGetHandler = new CookieGetHandler();

    Response actualResponse = cookieGetHandler.generate(request);

    assertEquals("Set-Cookie: type=chocolate\r\nContent-Type: text/plain\r\n", actualResponse.getHeaders());
    assertEquals("Eat", new String (actualResponse.getBody()));
  }

  @Test
  public void newResponseCreatedForRequestSentWithValidCookie() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/eat_cookie")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost\r\nCookie: type=chocolate\r\n")
        .build();
    CookieGetHandler cookieGetHandler = new CookieGetHandler();

    Response actualResponse = cookieGetHandler.generate(request);

    assertEquals("mmmm chocolate", new String (actualResponse.getBody()));
  }
}