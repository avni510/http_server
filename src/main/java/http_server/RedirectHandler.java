package http_server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RedirectHandler implements Handler {

  public Response generate(Request request) throws IOException {
    Map<String, String> header = new HashMap();
    header.put("Location", "/");
    Response response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(302)
        .setHeaders(header)
        .build();
    return response;
  }
}
