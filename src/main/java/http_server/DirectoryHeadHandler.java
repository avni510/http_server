package http_server;

import java.io.IOException;

public class DirectoryHeadHandler implements Handler{

  public Response generate(Request request) throws IOException {
    Response response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .build();
    return response;
  }
}
