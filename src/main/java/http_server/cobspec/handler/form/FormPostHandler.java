package http_server.cobspec.handler.form;

import http_server.core.DataStore;
import http_server.core.response.Response;
import http_server.core.response.ResponseBuilder;

import http_server.core.Handler;

import http_server.core.request.Request;

import java.io.IOException;

public class FormPostHandler implements Handler {
  private DataStore<String, String> dataStore;
  private String parameter = "data";

  public FormPostHandler(DataStore<String, String> dataStore) {
    this.dataStore = dataStore;
  }

  public Response generate(Request request) throws IOException {
    String dataValue =  getDataValue(request);
    storeData(dataValue);
    Response response = new ResponseBuilder()
                 .setHttpVersion("HTTP/1.1")
                 .setStatusCode(200)
                 .build();
    return response;
  }

  private String getDataValue(Request request){
    return request.getBodyParam(parameter);
  }

  private void storeData(String dataValue){
    dataStore.storeEntry(parameter, dataValue);
  }
}
