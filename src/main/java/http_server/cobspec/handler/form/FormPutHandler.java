package http_server.cobspec.handler.form;

import http_server.core.DataStore;
import http_server.core.response.Response;
import http_server.core.response.ResponseBuilder;

import http_server.core.Handler;

import http_server.core.request.Request;

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
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .build();
  }

  private String getDataValue(Request request){
    return request.getBodyParam(parameter);
  }

  private void storeData(String dataValue){
    dataStore.storeEntry(parameter, dataValue);
  }
}
