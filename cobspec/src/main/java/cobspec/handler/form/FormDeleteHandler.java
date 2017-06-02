package cobspec.handler.form;

import core.DataStore;
import core.response.Response;
import core.response.ResponseBuilder;

import core.Handler;

import core.request.Request;

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
