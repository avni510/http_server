package http_server.handler;

import http_server.Handler;

import http_server.request.Request;

import http_server.response.Response;
import http_server.response.ResponseBuilder;

import java.io.IOException;

public class TeapotGetHandler implements Handler {

  public Response generate(Request request) throws IOException {
    if (request.getUri().equals("/coffee")) {
      return handleCoffeeRequest();
    } else if (request.getUri().equals("/tea")){
      return handleTeaRequest();
    }
    return null;
  }

  private Response handleCoffeeRequest(){
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(418)
        .setHeader("Content-Type", "text/plain")
        .setBody("I'm a teapot")
        .build();
  }

  private Response handleTeaRequest(){
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .build();
  }
}
