package restful.handler.users;

import core.DataStore;
import core.Handler;

import core.handler.ErrorHandler;

import core.request.Request;

import core.response.Response;
import core.response.ResponseBuilder;

import java.io.IOException;

public class UpdateUsersHandler implements Handler{
  DataStore<Integer, String> dataStore;
  String parameter = "username";

  public UpdateUsersHandler(DataStore<Integer, String> dataStore){
    this.dataStore = dataStore;
  }

  public Response generate(Request request) throws IOException {
    Integer id = request.getIdInUri();
    if (dataStore.keyExists(id)) {
      String usernameValue = request.getBodyParam(parameter);
      dataStore.storeEntry(id, usernameValue);
      return new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(200)
          .build();
    } else {
      Handler handler = new ErrorHandler(404);
      return handler.generate(request);
    }
  }
}
