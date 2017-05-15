package http_server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CookieHandler implements Handler{
  private String cookie = "type=chocolate";

  public Response generate(Request request) throws IOException {
    Response response = null;
    String cookieKeyAndValue = cookieValue(request.getHeader());
    if (cookie.equals(cookieKeyAndValue)){
      Map<String, String> header = new HashMap();
      header.put("Content-Type", "text/plain");
      response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(200)
          .setHeaders(header)
          .setBody("mmmm chocolate")
          .build();

    } else {
      Map<String, String> header = new HashMap();
      header.put("Set-Cookie", cookie);
      header.put("Content-Type", "text/plain");
      response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(200)
          .setHeaders(header)
          .setBody("Eat")
          .build();
    }
    return response;
  }

  private String cookieValue(ArrayList<String> headers){
    String cookie = null;
    for (String header: headers) {
      if (header.contains("Cookie")){
        cookie = header;
      }
    }
    if (cookie == null) {
      return cookie;
    }
    String[] cookieParts = cookie.split(": ");
    return cookieParts[1];
  }
}
