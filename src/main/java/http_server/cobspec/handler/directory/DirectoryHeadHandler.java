package http_server.cobspec.handler.directory;

import http_server.core.Handler;

import http_server.core.request.Request;

import http_server.core.response.Response;
import http_server.core.response.ResponseBuilder;

import java.io.IOException;

public class DirectoryHeadHandler implements Handler {

  public Response generate(Request request) throws IOException {
    Response response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .build();
    return response;
  }
}
