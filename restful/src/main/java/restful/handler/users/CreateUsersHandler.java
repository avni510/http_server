package restful.handler.users;

import core.DataStore;
import core.Handler;
import core.HttpCodes;

import core.request.Request;

import core.response.Response;
import core.response.ResponseBuilder;

import java.io.IOException;

public class CreateUsersHandler implements Handler {
  private DataStore<Integer, String> dataStore;
  private String parameter = "username";

  public CreateUsersHandler(DataStore<Integer, String> dataStore) {
    this.dataStore = dataStore;
  }

  public Response generate(Request request) throws IOException {
    String usernameValue = request.getBodyParam(parameter);
    storeEntry(usernameValue);
    return new ResponseBuilder()
                .setStatusCode(HttpCodes.OK)
        .build();
  }

  private void storeEntry(String usernameValue) {
    int lastEntryIndex = dataStore.count();
    Integer newIndex = lastEntryIndex + 1;
    dataStore.storeEntry(newIndex, usernameValue);
  }
}
