package restful.handler.users;

import core.DataStore;
import core.Handler;
import core.handler.ErrorHandler;
import core.request.Request;
import core.response.Response;
import core.response.ResponseBuilder;


import java.io.IOException;

public class ShowUsersHandler implements Handler {
  private DataStore<Integer, String> dataStore;

  public ShowUsersHandler(DataStore dataStore){
    this.dataStore = dataStore;
  }

  public Response generate(Request request) throws IOException {
    Integer id = request.getIdInUri();
    if (dataStore.keyExists(id)) {
      String username = dataStore.getValue(id);
      return new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(200)
          .setHeader("Content-Type", "text/plain")
          .setBody(username)
          .build();
    } else {
      Handler handler = new ErrorHandler(404);
      return handler.generate(request);
    }
  }
}