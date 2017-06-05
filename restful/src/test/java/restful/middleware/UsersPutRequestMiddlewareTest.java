package restful.middleware;

import core.DataStore;
import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UsersPutRequestMiddlewareTest {

  @Test
  public void responseForPutRequestIsReturned() throws Exception {
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    UsersShowMiddleware usersShowMiddleware = new UsersShowMiddleware(dataStore);
    UsersEditMiddleware app = new UsersEditMiddleware(usersShowMiddleware);
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.PUT)
        .setUri("/users/1")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .setBody("username=bar")
        .build();
    UsersPutRequestMiddleware usersPutRequestMiddleware = new UsersPutRequestMiddleware(dataStore, app);

    Response actualResponse = usersPutRequestMiddleware.call(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals(dataStore.getValue(1), "bar");
  }

  @Test
  public void responseForGetRequestEditingUsernameIsReturned() throws Exception {
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    UsersShowMiddleware usersShowMiddleware = new UsersShowMiddleware(dataStore);
    UsersEditMiddleware app = new UsersEditMiddleware(usersShowMiddleware);
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users/1/edit")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    UsersPutRequestMiddleware usersPutRequestMiddleware = new UsersPutRequestMiddleware(dataStore, app);

    Response actualResponse = usersPutRequestMiddleware.call(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}