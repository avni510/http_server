package restful.handler.users;

import core.DataStore;
import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class UsersEditHandlerTest {

  @Test
  public void editRequestIsHandled() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users/1/edit")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    UsersEditHandler usersEditHandler = new UsersEditHandler(dataStore);

    Response actualResponse = usersEditHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }

  @Test
  public void idInUriDoesNotExistInDataStore() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users/500/edit")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    UsersEditHandler usersEditHandler = new UsersEditHandler(dataStore);

    Response actualResponse = usersEditHandler.generate(request);

    assertEquals("404 Not Found", actualResponse.getStatusCodeMessage());
  }
}