package restful.handler.users;

import core.DataStore;
import core.Handler;

import core.request.Request;

import core.response.Response;
import core.response.ResponseBuilder;

import java.io.IOException;

public class UsersDeleteHandler implements Handler {
  private DataStore<Integer, String> dataStore;

  public UsersDeleteHandler(DataStore<Integer, String> dataStore) {
    this.dataStore = dataStore;
  }


  public Response generate(Request request) throws IOException {
    deleteUsername(request);
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .build();
  }

  private void deleteUsername(Request request) {
    Integer id = request.getIdInUri();
    dataStore.delete(id);
  }
}
