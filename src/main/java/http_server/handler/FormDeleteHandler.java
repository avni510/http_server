package http_server.handler;

import http_server.*;

import java.io.IOException;

public class FormDeleteHandler implements Handler {
  private DataStore dataStore;

  public FormDeleteHandler(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  public Response generate(Request request) throws IOException {
    dataStore.clear();
    Response response = new ResponseBuilder()
                .setHttpVersion("HTTP/1.1")
                .setStatusCode(200)
                .build();
    return response;
  }
}
