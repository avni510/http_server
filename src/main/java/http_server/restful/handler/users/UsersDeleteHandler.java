package http_server.restful.handler.users;

import http_server.DataStore;
import http_server.Handler;

import http_server.request.Request;

import http_server.response.Response;
import http_server.response.ResponseBuilder;

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
