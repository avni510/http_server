package http_server.cobspec.handler.form;

import http_server.DataStore;
import http_server.response.Response;
import http_server.response.ResponseBuilder;

import http_server.Handler;

import http_server.request.Request;

import java.io.IOException;

public class FormGetHandler implements Handler {
  private DataStore<String, String> dataStore;
  private String parameter = "data";

  public FormGetHandler(DataStore<String, String> dataStore) {
    this.dataStore = dataStore;
  }

  public Response generate(Request request) throws IOException {
    String dataValue = getDataValue();
    Response response = new ResponseBuilder()
              .setHttpVersion("HTTP/1.1")
              .setStatusCode(200)
              .setHeader("Content-Type", "text/html")
              .setBody(getBody(dataValue))
              .build();
    return response;
  }

  private String getDataValue() {
    if (!dataStore.isStoreEmpty()){
      String parameterValue = dataStore.getValue(parameter);
      return parameter + "=" + parameterValue;
    } else {
      return "";
    }
  }

  private String getBody(String bodyValue) {
    return
        "<form action=\"/form\" method=\"post\">" +
          "Data: <br> " +
          "<input type=\"text\" name=\"" + parameter + "\">" +
          "<input type=\"submit\" value=\"Submit\">" +
        "</form> <br>" + "<p>" + bodyValue + "</p>";
  }
}
