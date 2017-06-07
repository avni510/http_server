package cobspec.handler.methods;

import core.Handler;
import core.HttpCodes;

import core.request.Request;
import core.request.RequestMethod;

import core.response.Response;
import core.response.ResponseBuilder;

import java.io.IOException;

public class MethodsOptionsHandler implements Handler {
  private RequestMethod[] requestMethodsOnResource;

  public MethodsOptionsHandler(RequestMethod[] requestMethodsOnResource) {
    this.requestMethodsOnResource = requestMethodsOnResource;
  }

  public Response generate(Request request) throws IOException {
    String allAllowedRequestMethods = requestMethodsToString();
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(HttpCodes.OK)
        .setHeader("Allow", allAllowedRequestMethods)
        .build();
  }

  private String requestMethodsToString() {
    StringBuilder allRequestMethods = new StringBuilder();
    Integer lastIndex = requestMethodsOnResource.length - 1;
    for (int i = 0; i < lastIndex; i++) {
      allRequestMethods.append(requestMethodsOnResource[i].toString() + ",");
    }
    Enum<RequestMethod> lastElement = requestMethodsOnResource[lastIndex];
    allRequestMethods.append(lastElement);
    return allRequestMethods.toString();
  }
}
