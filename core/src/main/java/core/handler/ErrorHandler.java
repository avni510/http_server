package core.handler;

import core.Handler;

import core.request.Request;

import core.response.Response;
import core.response.ResponseBuilder;

public class ErrorHandler implements Handler {
  private String errorCode;

  public ErrorHandler(String errorCode) {
    this.errorCode = errorCode;
  }

  public Response generate(Request request) {
    return new ResponseBuilder()
        .setStatusCode(errorCode)
        .build();
  }
}
