package http_server.core;

import http_server.core.request.Request;

import http_server.core.response.Response;
import http_server.core.response.ResponseBuilder;

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
