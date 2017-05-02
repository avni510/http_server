package http_server;

public class ErrorHandler {
  private Integer errorCode;

  public ErrorHandler(Integer errorCode){
    this.errorCode = errorCode;
  }

  public String generate(){
    Response response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(errorCode)
        .build();
    return response.getHttpResponse();
  }
}
