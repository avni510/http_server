package http_server.cobspec.handler.form;

import http_server.core.DataStore;
import http_server.core.response.Response;
import http_server.core.response.ResponseBuilder;

import http_server.core.Handler;

import http_server.core.request.Request;

import java.io.IOException;

public class FormDeleteHandler implements Handler {
  private DataStore<String, String> dataStore;

  public FormDeleteHandler(DataStore<String, String> dataStore) {
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
