package http_server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OptionsHandler implements Handler{
  private RequestMethod[] requestMethodsOnResource;

  public OptionsHandler(RequestMethod[] requestMethodsOnResource){
    this.requestMethodsOnResource = requestMethodsOnResource;
  }

  public String generate(Request request) throws IOException {
    if (request.getRequestMethod() == RequestMethod.OPTIONS) {
      Map<String, String> header = new HashMap<>();
      String allAllowedRequestMethods = requestMethodsToString();
      header.put("Allow", allAllowedRequestMethods);
      Response response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(200)
          .setHeaders(header)
          .build();
      return response.getHttpResponse();
    }
    Response response = new ResponseBuilder()
                        .setHttpVersion("HTTP/1.1")
                        .setStatusCode(200)
                        .build();
    return response.getHttpResponse();
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
