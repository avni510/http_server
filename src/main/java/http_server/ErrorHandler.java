package http_server;

public class ErrorHandler implements Handler{
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
