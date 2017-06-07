package restful.handler.users;

import core.utils.DataStore;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class EditUsersHandlerTest {

  @Test
  public void editRequestIsHandled() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users/1/edit")
                .setHeader("Host: localhost")
        .build();
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    EditUsersHandler editUsersHandler = new EditUsersHandler(dataStore);

    Response actualResponse = editUsersHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }

  @Test
  public void idInUriDoesNotExistInDataStore() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users/500/edit")
                .setHeader("Host: localhost")
        .build();
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    EditUsersHandler editUsersHandler = new EditUsersHandler(dataStore);

    Response actualResponse = editUsersHandler.generate(request);

    assertEquals("404 Not Found", actualResponse.getStatusCodeMessage());
  }
}