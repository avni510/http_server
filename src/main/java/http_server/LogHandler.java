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
    Response response;
    if (isAuthorized(request)) {
    response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeader("Content-Type", "plain/text")
        .setBody(getBody())
        .build();
    } else {
      response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(401)
          .setHeader("WWW-Authenticate", "Basic realm=\"Access to Avni's Server\"")
          .build();
    }
    return response;
  }

  private boolean isAuthorized(Request request) throws UnsupportedEncodingException {
    String authorizedHeader = request.getHeaderValue("Authorization");
    if (authorizedHeader != null) {
      String[] authorizationHeaderParts = authorizedHeader.split(" ");
      String encodedAuthorization = authorizationHeaderParts[1];
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
