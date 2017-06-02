package core.handler;

import core.Handler;

import core.request.Request;

import core.response.Response;
import core.response.ResponseBuilder;

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
