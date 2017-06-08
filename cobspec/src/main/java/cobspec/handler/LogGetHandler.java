package cobspec.handler;

import core.Handler;
import core.HttpCodes;

import core.request.Request;

import core.response.Response;
import core.response.ResponseBuilder;

import core.utils.DataStore;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.Base64;
import java.util.Map;

public class LogGetHandler implements Handler {
  DataStore<String, String> dataStore;
  String username;
  String password;

  public LogGetHandler(DataStore<String, String> dataStore, String username, String password) {
    this.dataStore = dataStore;
    this.username = username;
    this.password = password;
  }

  public Response generate(Request request) throws IOException {
    if (isAuthorized(request)) {
      return handleAuthorizedRequest();
    } else {
      return handleUnAuthorizedRequest();
    }
  }

  private Response handleAuthorizedRequest() {
    return new ResponseBuilder()
        .setStatusCode(HttpCodes.OK)
        .setHeader("Content-Type", "plain/text")
        .setBody(getBody())
        .build();
  }

  private Response handleUnAuthorizedRequest() {
    return new ResponseBuilder()
        .setStatusCode(HttpCodes.UNAUTHORIZED)
        .setHeader("WWW-Authenticate", "Basic realm=\"Access to Avni's Server\"")
        .build();
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

  private String getBody() {
    StringBuilder body = new StringBuilder();
    Map<String, String> allLogs = dataStore.getData();
    for (Map.Entry<String, String> data : allLogs.entrySet()) {
      body.append(data.getKey() + " " + data.getValue() + " ");
    }
    return body.toString();
  }
}
