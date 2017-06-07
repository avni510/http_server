package core.handler;

import core.Handler;

import core.HttpCodes;
import core.request.Request;

import core.response.Response;
import core.response.ResponseBuilder;

import java.io.UnsupportedEncodingException;

public class BaseHandler implements Handler {

  public Response generate(Request request) throws UnsupportedEncodingException {
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(HttpCodes.OK)
        .setHeader("Content-Type", "text/plain")
        .setBody("hello world")
        .build();
  }
}
