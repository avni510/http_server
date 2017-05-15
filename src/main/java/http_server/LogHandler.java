package http_server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class LogHandler implements Handler{
  DataStore dataStore;
  String username;
  String password;

  public LogHandler(DataStore dataStore, String username, String password){
    this.dataStore = dataStore;
    this.username = username;
    this.password = password;
  }

  public Response generate(Request request) throws IOException {
    Response response = null;
    if (isAuthorized(request)) {
    Map<String, String> header = new HashMap();
    header.put("Content-Type", "plain/text");
    response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeaders(header)
        .setBody(getBody())
        .build();
    } else {
      Map<String, String> header = new HashMap();
      header.put("WWW-Authenticate", "Basic");
      header.put("realm=", "\"Access to Avni's Server\"");
      response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(401)
          .setHeaders(header)
          .build();
    }
    return response;
  }

  private boolean isAuthorized(Request request) throws UnsupportedEncodingException {
    String firstHeader = request.getHeader().get(0);
    if (firstHeader.contains("Authorization")) {
      String[] authorizationHeaderParts = firstHeader.split(" ");
      String encodedAuthorization = authorizationHeaderParts[2];
      String decodedAuthorization = new String(Base64.getDecoder().decode(encodedAuthorization), "UTF-8");
      String[] authorizationParts = decodedAuthorization.split(":");
      return authorizationParts[0].contains(username) && authorizationParts[1].contains(password);
    }
    return false;
  }

  private String getBody(){
    StringBuilder body = new StringBuilder();
    Map<String, String> allLogs = dataStore.getData();
    for (Map.Entry<String, String> data : allLogs.entrySet()) {
      body.append(data.getKey() + " " + data.getValue() + " ");
    }
    return body.toString();
  }
}
