package http_server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TeapotHandler implements Handler{

  public Response generate(Request request) throws IOException {
    Response response = null;
    if (request.getUri().equals("/coffee")) {
      Map<String, String> header = new HashMap();
      header.put("Content-Type", "text/plain");
      response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(418)
          .setHeaders(header)
          .setBody("I'm a teapot")
          .build();
    } else if (request.getUri().equals("/tea")){
      response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(200)
          .build();
    }
   return response;
  }
}
