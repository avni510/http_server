package restful.handler.users;

import core.DataStore;
import core.Handler;

import core.request.Request;

import core.response.Response;
import core.response.ResponseBuilder;

import java.io.IOException;

public class UsersPostHandler implements Handler{
  private DataStore<Integer, String> dataStore;
  private String parameter = "username";

  public UsersPostHandler(DataStore<Integer, String> dataStore){
    this.dataStore = dataStore;
  }

  public Response generate(Request request) throws IOException {
    String usernameValue = request.getBodyParam(parameter);
    storeEntry(usernameValue);
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .build();
  }

  private void storeEntry(String usernameValue){
    int lastEntryIndex = dataStore.count();
    Integer newIndex = lastEntryIndex + 1;
    dataStore.storeEntry(newIndex, usernameValue);
  }
}
