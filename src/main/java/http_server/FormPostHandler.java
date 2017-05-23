package http_server;

import java.io.IOException;

public class FormPostHandler implements Handler{
  private DataStore dataStore;
  private String parameter = "data";

  public FormPostHandler(DataStore dataStore) {
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
