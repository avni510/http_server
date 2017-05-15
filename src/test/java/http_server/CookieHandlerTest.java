package http_server;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class CookieHandlerTest {

  @Test
  public void cookieIsSentBack() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/cookie?type=chocolate")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .build();
    CookieHandler cookieHandler = new CookieHandler();

    Response actualResponse = cookieHandler.generate(request);

    assertEquals("Set-Cookie: type=chocolate\r\nContent-Type: text/plain\r\n", actualResponse.getHeaders());
    assertEquals("Eat", new String (actualResponse.getBody()));
  }

  @Test
  public void newResponseCreatedForRequestSentWithValidCookie() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/eat_cookie")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost", "Cookie: type=chocolate")))
        .build();
    CookieHandler cookieHandler = new CookieHandler();

    Response actualResponse = cookieHandler.generate(request);

    assertEquals("mmmm chocolate", new String (actualResponse.getBody()));
  }
}