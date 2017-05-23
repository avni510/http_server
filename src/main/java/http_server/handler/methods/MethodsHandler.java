package http_server.handler.methods;

import http_server.Handler;
import http_server.Request;
import http_server.Response;
import http_server.ResponseBuilder;

import java.io.IOException;

public class MethodsHandler implements Handler {

  public Response generate(Request request) throws IOException {
    Response response = new ResponseBuilder()
                        .setHttpVersion("HTTP/1.1")
                        .setStatusCode(200)
                        .build();
    return response;
  }
}
