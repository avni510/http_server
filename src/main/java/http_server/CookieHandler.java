package http_server;

import java.io.IOException;

public class CookieHandler implements Handler{
  private String cookie = "type=chocolate";

  public Response generate(Request request) throws IOException {
    Response response;
    String cookieKeyAndValue = cookieValue(request);
    if (cookie.equals(cookieKeyAndValue)){
      response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(200)
          .setHeader("Content-Type", "text/plain")
          .setBody("mmmm chocolate")
          .build();

    } else {
      response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(200)
          .setHeader("Content-Type", "text/plain")
          .setHeader("Set-Cookie", cookie)
          .setBody("Eat")
          .build();
    }
    return response;
  }

  private String cookieValue(Request request){
    String cookie = null;
    String headerValue = request.getHeaderValue("Cookie");
    if (headerValue != null){
      cookie = headerValue;
    }
    return cookie;
  }
}
