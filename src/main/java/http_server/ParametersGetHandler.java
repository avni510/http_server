package http_server;

import java.io.IOException;

import java.net.URLDecoder;

public class ParametersGetHandler implements Handler{

  public Response generate(Request request) throws IOException {
    Response response = new ResponseBuilder().
        setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeader("Content-Type", "text/plain")
        .setBody(splitUri(request.getUri()))
        .build();
    return response;
  }

  private String splitUri(String uri) {
    if (uri.contains("?")) {
      String[] splitUri = uri.split("\\?");
      return decodedParameterComponents(splitUri[1]);
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

  private String decodedParameterComponents(String uri) {
    StringBuilder decodedParameters = new StringBuilder();
    String[] splitUri = uri.split("&");
    for(String param: splitUri){
      String[] splitParameters = param.split("=");
      decodedParameters.append(splitParameters[0] + " = " + decodeString(splitParameters[1]) + " ");
    }
    return decodedParameters.toString();
  }
}
