package http_server;

import java.io.IOException;

public class FormHandlerDelete implements Handler{
  private DataStore dataStore;

  public FormHandlerDelete(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  public Response generate(Request request) throws IOException {
    dataStore.clear();
    Response response = new ResponseBuilder()
                .setHttpVersion("HTTP/1.1")
                .setStatusCode(200)
                .build();
    return response;
  }
}
