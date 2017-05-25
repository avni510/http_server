package http_server.handler.methods;

import http_server.Handler;

import http_server.request.Request;
import http_server.request.RequestMethod;

import http_server.response.Response;
import http_server.response.ResponseBuilder;

import java.io.IOException;

public class MethodsOptionsHandler implements Handler {
  private RequestMethod[] requestMethodsOnResource;

  public MethodsOptionsHandler(RequestMethod[] requestMethodsOnResource){
    this.requestMethodsOnResource = requestMethodsOnResource;
  }

  public Response generate(Request request) throws IOException {
    String allAllowedRequestMethods = requestMethodsToString();
    Response response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeader("Allow", allAllowedRequestMethods)
        .build();
    return response;
  }

  private String requestMethodsToString(){
    StringBuilder allRequestMethods = new StringBuilder();
    Integer lastIndex = requestMethodsOnResource.length - 1;
    for(int i = 0; i < lastIndex; i++){
      allRequestMethods.append(requestMethodsOnResource[i].toString() + ",");
    }
    Enum<RequestMethod> lastElement = requestMethodsOnResource[lastIndex];
    allRequestMethods.append(lastElement);
    return allRequestMethods.toString();
  }
}
