package http_server.handler.form;

import http_server.response.Response;
import http_server.response.ResponseBuilder;

import http_server.Handler;
import http_server.DataStore;

import http_server.request.Request;

import java.io.IOException;

public class FormPutHandler implements Handler {
  private DataStore dataStore;
  private String parameter = "data";

  public FormPutHandler(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  public Response generate(Request request) throws IOException {
    String parameterValue = request.getBodyParam(parameter);
    dataStore.storeEntry(parameter, parameterValue);
    Response response = new ResponseBuilder()
                .setHttpVersion("HTTP/1.1")
                .setStatusCode(200)
                .build();
    return response;
  }
}
