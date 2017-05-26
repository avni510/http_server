package http_server.handler;

import http_server.Handler;

import http_server.request.Request;

import http_server.response.Response;
import http_server.response.ResponseBuilder;

public class ErrorHandler implements Handler {
  private Integer errorCode;

  public ErrorHandler(Integer errorCode){
    this.errorCode = errorCode;
  }

  public Response generate(Request request){
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(errorCode)
        .build();
  }
}
