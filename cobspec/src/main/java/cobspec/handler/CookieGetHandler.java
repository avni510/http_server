package cobspec.handler;

import core.Handler;
import core.HttpCodes;

import core.request.Request;

import core.response.Response;
import core.response.ResponseBuilder;

import java.io.IOException;

public class CookieGetHandler implements Handler {
  private String cookie = "type=chocolate";

  public Response generate(Request request) throws IOException {
    String requestCookie = cookieValue(request);
    if (cookie.equals(requestCookie)) {
      return handleCookieRequest();
    } else {
      return handleSetCookie();
    }
  }

  private Response handleCookieRequest() {
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(HttpCodes.OK)
        .setHeader("Content-Type", "text/plain")
        .setBody("mmmm chocolate")
        .build();
  }

  private Response handleSetCookie() {
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(HttpCodes.OK)
        .setHeader("Content-Type", "text/plain")
        .setHeader("Set-Cookie", cookie)
        .setBody("Eat")
        .build();
  }

  private String cookieValue(Request request) {
    String cookie = null;
    String headerValue = request.getHeaderValue("Cookie");
    if (headerValue != null) {
      cookie = headerValue;
    }
    return cookie;
  }
}
