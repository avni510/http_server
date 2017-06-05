package restful.handler.users;

import core.DataStore;
import core.Handler;
import core.request.Request;
import core.response.Response;
import core.response.ResponseBuilder;


import java.io.IOException;

public class UsersShowHandler implements Handler {
  private DataStore<Integer, String> dataStore;

  public UsersShowHandler(DataStore dataStore){
    this.dataStore = dataStore;
  }

  public Response generate(Request request) throws IOException {
    Integer id = request.getIdInUri();
    String username = dataStore.getValue(id);
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeader("Content-Type", "text/plain")
        .setBody(username)
        .build();
  }
}
