package core;

import core.request.Request;

import core.response.Response;
import core.response.ResponseBuilder;

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
