package http_server.handler;

import http_server.Handler;

import http_server.request.Request;

import http_server.response.Response;
import http_server.response.ResponseBuilder;

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
