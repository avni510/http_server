package cobspec.handler.form;

import core.DataStore;
import core.HttpCodes;
import core.Handler;

import core.response.Response;
import core.response.ResponseBuilder;

import core.request.Request;

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
        .setStatusCode(HttpCodes.OK)
        .setHeader("Content-Type", "text/html")
        .setBody(getBody(dataValue))
        .build();
    return response;
  }

  private String getDataValue() {
    if (!dataStore.isStoreEmpty()) {
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
