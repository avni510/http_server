package http_server.cobspec.handler;

import http_server.Handler;

import http_server.request.Request;

import http_server.response.Response;
import http_server.response.ResponseBuilder;

import java.io.IOException;

import java.net.URLDecoder;

public class ParametersGetHandler implements Handler {

  public Response generate(Request request) throws IOException {
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeader("Content-Type", "text/plain")
        .setBody(decodeUri(request.getUri()))
        .build();
  }

  private String decodeUri(String uri) {
    if (uri.contains("?")) {
      String[] splitUri = uri.split("\\?");
      String encodedParameter = splitUri[1];
      return decodedParameter(encodedParameter);
    } else {
      return uri;
    }
  }

  private String decodeString(String uri) {
    String decodedString = null;
    try {
      decodedString = URLDecoder.decode(uri, "UTF-8");
    } catch (Exception e) {
      System.out.println(e.getStackTrace());
    }
    return decodedString;
  }

  private String decodedParameter(String uri) {
    StringBuilder decodedParameters = new StringBuilder();
    String[] encodedSplitParameters = uri.split("&");
    for(String param: encodedSplitParameters){
      String[] splitKeyAndValue = param.split("=");
      String variableName = splitKeyAndValue[0];
      String variableValue = splitKeyAndValue[1];
      decodedParameters.append(variableName + " = " + decodeString(variableValue) + " ");
    }
    return decodedParameters.toString();
  }
}
