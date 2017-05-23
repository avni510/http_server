package http_server.handler.form;

import http_server.Response;
import http_server.Handler;
import http_server.DataStore;
import http_server.Request;
import http_server.ResponseBuilder;

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
