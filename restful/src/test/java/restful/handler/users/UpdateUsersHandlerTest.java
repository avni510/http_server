package restful.handler.users;

import core.utils.DataStore;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UpdateUsersHandlerTest {

  @Test
  public void handlesAPutRequest() throws IOException {
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.PUT)
        .setUri("/users/1")
                .setHeader("Host: localhost")
        .setBody("username=bar")
        .build();

    UpdateUsersHandler updateUsersHandler = new UpdateUsersHandler(dataStore);

    Response actualResponse = updateUsersHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals(dataStore.getValue(1), "bar");
  }

  @Test
  public void idInUriDoesNotExistInDataStore() throws IOException {
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.PUT)
        .setUri("/users/500")
                .setHeader("Host: localhost")
        .setBody("username=bar")
        .build();

    UpdateUsersHandler updateUsersHandler = new UpdateUsersHandler(dataStore);

    Response actualResponse = updateUsersHandler.generate(request);

    assertEquals("404 Not Found", actualResponse.getStatusCodeMessage());
  }
}