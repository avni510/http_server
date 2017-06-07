package cobspec.handler.form;

import core.DataStore;
import core.HttpCodes;
import core.Handler;

import core.response.Response;
import core.response.ResponseBuilder;

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
        .setStatusCode(HttpCodes.OK)
        .build();
  }

  private void deleteAllData() {
    dataStore.clear();
  }
}
