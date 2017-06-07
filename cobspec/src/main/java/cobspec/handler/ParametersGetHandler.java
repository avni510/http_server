package cobspec.handler;

import core.Handler;
import core.HttpCodes;

import core.request.Request;

import core.response.Response;
import core.response.ResponseBuilder;

import java.io.IOException;

import java.net.URLDecoder;

public class ParametersGetHandler implements Handler {

  public Response generate(Request request) throws IOException {
    return new ResponseBuilder()
        .setStatusCode(HttpCodes.OK)
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
    for (String param : encodedSplitParameters) {
      String[] splitKeyAndValue = param.split("=");
      String variableName = splitKeyAndValue[0];
      String variableValue = splitKeyAndValue[1];
      decodedParameters.append(variableName + " = " + decodeString(variableValue) + " ");
    }
    return decodedParameters.toString();
  }
}
