package http_server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RedirectGetHandler implements Handler {

  public Response generate(Request request) throws IOException {
    Response response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(302)
        .setHeader("Location", "/")
        .build();
    return response;
  }
}
