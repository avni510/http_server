package http_server.cobspec.handler;

import http_server.core.Handler;

import http_server.core.request.Request;

import http_server.core.response.Response;
import http_server.core.response.ResponseBuilder;

import java.io.UnsupportedEncodingException;

public class HelloWorldGetHandler implements Handler {

  public Response generate(Request request) throws UnsupportedEncodingException {
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeader("Content-Type", "text/plain")
        .setBody("hello world")
        .build();
  }
}
