package http_server.handler.form;

import http_server.response.Response;
import http_server.response.ResponseBuilder;

import http_server.Handler;
import http_server.DataStore;

import http_server.request.Request;

import java.io.IOException;

public class FormDeleteHandler implements Handler {
  private DataStore dataStore;

  public FormDeleteHandler(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  public Response generate(Request request) throws IOException {
    deleteAllData();
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .build();
  }

  private void deleteAllData(){
    dataStore.clear();
  }
}
