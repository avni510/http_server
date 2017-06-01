package http_server.restful.handler.users;

import http_server.core.DataStore;
import http_server.core.Handler;

import http_server.core.request.Request;

import http_server.core.response.Response;
import http_server.core.response.ResponseBuilder;

import java.io.IOException;

public class UsersPutHandler implements Handler{
  DataStore<Integer, String> dataStore;
  String parameter = "username";

  public UsersPutHandler(DataStore<Integer, String> dataStore){
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
    Integer id = request.getIdInUri();
    dataStore.storeEntry(id, usernameValue);
  }
}
