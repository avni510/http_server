package http_server.handler;

import http_server.Handler;
import http_server.Request;
import http_server.Response;
import http_server.ResponseBuilder;

public class ErrorHandler implements Handler {
  private Integer errorCode;

  public ErrorHandler(Integer errorCode){
    this.errorCode = errorCode;
  }

  public Response generate(Request request){
    Response response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(errorCode)
        .build();
    return response;
  }
}
