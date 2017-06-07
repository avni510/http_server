package restful.handler.users;

import core.DataStore;
import core.Handler;
import core.HttpCodes;

import core.handler.ErrorHandler;

import core.request.Request;

import core.response.Response;
import core.response.ResponseBuilder;

import java.io.IOException;

public class DeleteUsersHandler implements Handler {
  private DataStore<Integer, String> dataStore;

  public DeleteUsersHandler(DataStore<Integer, String> dataStore) {
    this.dataStore = dataStore;
  }


  public Response generate(Request request) throws IOException {
    Integer id = request.getIdInUri();
    if (dataStore.keyExists(id)) {
      deleteUsername(request);
      return new ResponseBuilder()
          .setStatusCode(HttpCodes.OK)
          .build();
    } else {
      Handler handler = new ErrorHandler(HttpCodes.NOT_FOUND);
      return handler.generate(request);
    }
  }

  private void deleteUsername(Request request) {
    Integer id = request.getIdInUri();
    dataStore.delete(id);
  }
}
