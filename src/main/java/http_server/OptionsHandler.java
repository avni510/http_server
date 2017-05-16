package http_server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OptionsHandler implements Handler{
  private RequestMethod[] requestMethodsOnResource;

  public OptionsHandler(RequestMethod[] requestMethodsOnResource){
    this.requestMethodsOnResource = requestMethodsOnResource;
  }

  public Response generate(Request request) throws IOException {
    if (request.getRequestMethod() == RequestMethod.OPTIONS) {
      String allAllowedRequestMethods = requestMethodsToString();
      Response response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(200)
          .setHeader("Allow", allAllowedRequestMethods)
          .build();
      return response;
    }
    Response response = new ResponseBuilder()
                        .setHttpVersion("HTTP/1.1")
                        .setStatusCode(200)
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
