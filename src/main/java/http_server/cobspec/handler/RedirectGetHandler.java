package http_server.cobspec.handler;

import http_server.core.Handler;

import http_server.core.request.Request;

import http_server.core.response.Response;
import http_server.core.response.ResponseBuilder;

import java.io.IOException;

public class RedirectGetHandler implements Handler {

  public Response generate(Request request) throws IOException {
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(302)
        .setHeader("Location", "/")
        .build();
  }
}
