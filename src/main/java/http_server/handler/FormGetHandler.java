package http_server.handler;

import http_server.*;

import java.io.IOException;

public class FormGetHandler implements Handler {
  private DataStore dataStore;
  private String parameter = "data";

  public FormGetHandler(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  public Response generate(Request request) throws IOException {
    String bodyValue = getBodyValue();
    Response response = new ResponseBuilder()
              .setHttpVersion("HTTP/1.1")
              .setStatusCode(200)
              .setHeader("Content-Type", "text/html")
              .setBody(getBody(bodyValue))
              .build();
    return response;
  }

  private String getBodyValue() {
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
