package http_server.cobspec.handler;

import http_server.core.Constants;

import http_server.core.request.Request;
import http_server.core.request.RequestBuilder;
import http_server.core.request.RequestMethod;

import http_server.core.response.Response;

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
        .setHeader("Host: localhost")
        .build();
    CookieGetHandler cookieGetHandler = new CookieGetHandler();

    Response actualResponse = cookieGetHandler.generate(request);

    assertEquals("Set-Cookie: type=chocolate" + Constants.CLRF +
                          "Content-Type: text/plain" + Constants.CLRF, actualResponse.getHeaders());
    assertEquals("Eat", new String (actualResponse.getBody()));
  }

  @Test
  public void newResponseCreatedForRequestSentWithValidCookie() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/eat_cookie")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost" + Constants.CLRF + "Cookie: type=chocolate" + Constants.CLRF)
        .build();
    CookieGetHandler cookieGetHandler = new CookieGetHandler();

    Response actualResponse = cookieGetHandler.generate(request);

    assertEquals("mmmm chocolate", new String (actualResponse.getBody()));
  }
}