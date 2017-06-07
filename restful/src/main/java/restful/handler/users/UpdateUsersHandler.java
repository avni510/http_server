package restful.handler.users;

import core.Handler;
import core.HttpCodes;

import core.handler.ErrorHandler;

import core.utils.DataStore;

import core.request.Request;

import core.response.Response;
import core.response.ResponseBuilder;

import java.io.IOException;

public class UpdateUsersHandler implements Handler {
  DataStore<Integer, String> dataStore;
  String parameter = "username";

  public UpdateUsersHandler(DataStore<Integer, String> dataStore) {
    this.dataStore = dataStore;
  }

  public Response generate(Request request) throws IOException {
    Integer id = request.getIdInUri();
    if (dataStore.keyExists(id)) {
      String usernameValue = request.getBodyParam(parameter);
      dataStore.storeEntry(id, usernameValue);
      return new ResponseBuilder()
                    .setStatusCode(HttpCodes.OK)
          .build();
    } else {
      Handler handler = new ErrorHandler(HttpCodes.NOT_FOUND);
      return handler.generate(request);
    }
  }
}
