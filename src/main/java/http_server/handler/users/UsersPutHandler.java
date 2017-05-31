package http_server.handler.users;

import http_server.DataStore;
import http_server.Handler;
import http_server.request.Request;
import http_server.response.Response;
import http_server.response.ResponseBuilder;

import java.io.IOException;

public class UsersPutHandler implements Handler{
  DataStore dataStore;
  String parameter = "username";

  public UsersPutHandler(DataStore dataStore){
    this.dataStore = dataStore;
  }

  public Response generate(Request request) throws IOException {
    String usernameValue = request.getBodyParam(parameter);
    storeEntry(request, usernameValue);
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .build();
  }

  private void storeEntry(Request request, String usernameValue){
    String uri = request.getUri();
    String[] uriParts = uri.split("/");
    String id = uriParts[uriParts.length - 1];
    dataStore.storeEntry(id, usernameValue);
  }
}
