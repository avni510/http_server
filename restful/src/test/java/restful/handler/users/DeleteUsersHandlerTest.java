package restful.handler.users;

import core.utils.DataStore;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DeleteUsersHandlerTest {

  @Test
  public void aDeleteRequestIsHandled() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.DELETE)
        .setUri("/users/1")
                .setHeader("Host: localhost")
        .build();
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    DeleteUsersHandler deleteUsersHandler = new DeleteUsersHandler(dataStore);

    Response actualResponse = deleteUsersHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals(dataStore.count(), 0);
  }

  @Test
  public void idInUriDoesNotExistInDataStore() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.DELETE)
        .setUri("/users/500")
                .setHeader("Host: localhost")
        .build();
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    DeleteUsersHandler deleteUsersHandler = new DeleteUsersHandler(dataStore);

    Response actualResponse = deleteUsersHandler.generate(request);

    assertEquals("404 Not Found", actualResponse.getStatusCodeMessage());
  }

  @Test
  public void noIdExistsInUri() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.DELETE)
        .setUri("/users/bar")
                .setHeader("Host: localhost")
        .build();
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    DeleteUsersHandler deleteUsersHandler = new DeleteUsersHandler(dataStore);

    Response actualResponse = deleteUsersHandler.generate(request);

    assertEquals("404 Not Found", actualResponse.getStatusCodeMessage());
  }
}