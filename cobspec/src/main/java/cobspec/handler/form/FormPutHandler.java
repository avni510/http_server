package cobspec.handler.form;

import core.DataStore;
import core.HttpCodes;
import core.Handler;

import core.response.Response;
import core.response.ResponseBuilder;

import core.request.Request;

import java.io.IOException;

public class FormPutHandler implements Handler {
  private DataStore<String, String> dataStore;
  private String parameter = "data";

  public FormPutHandler(DataStore<String, String> dataStore) {
    this.dataStore = dataStore;
  }

  public Response generate(Request request) throws IOException {
    String dataValue = getDataValue(request);
    storeData(dataValue);
    return new ResponseBuilder()
        .setStatusCode(HttpCodes.OK)
        .build();
  }

  private String getDataValue(Request request) {
    return request.getBodyParam(parameter);
  }

  private void storeData(String dataValue) {
    dataStore.storeEntry(parameter, dataValue);
  }
}
