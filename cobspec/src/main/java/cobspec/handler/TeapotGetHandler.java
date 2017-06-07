package cobspec.handler;

import core.Handler;
import core.HttpCodes;

import core.request.Request;

import core.response.Response;
import core.response.ResponseBuilder;

import java.io.IOException;

public class TeapotGetHandler implements Handler {

  public Response generate(Request request) throws IOException {
    if (request.getUri().equals("/coffee")) {
      return handleCoffeeRequest();
    } else if (request.getUri().equals("/tea")) {
      return handleTeaRequest();
    }
    return null;
  }

  private Response handleCoffeeRequest() {
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(HttpCodes.TEAPOT)
        .setHeader("Content-Type", "text/plain")
        .setBody("I'm a teapot")
        .build();
  }

  private Response handleTeaRequest() {
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(HttpCodes.OK)
        .build();
  }
}
