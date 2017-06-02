package restful.handler.users;

import core.DataStore;
import core.Handler;

import core.request.Request;

import core.response.Response;
import core.response.ResponseBuilder;

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
