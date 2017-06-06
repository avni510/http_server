package restful.middleware;

import core.DataStore;
import core.Handler;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UsersEditMiddlewareTest {

  @Test
  public void responseForGetRequestEditingUsernameIsReturned() throws Exception {
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    UsersShowMiddleware usersShowMiddleware = new UsersShowMiddleware(dataStore);
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users/1/edit")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    UsersEditMiddleware usersEditMiddleware = new UsersEditMiddleware(usersShowMiddleware);

    Handler handler = usersEditMiddleware.call(request);

    Response actualResponse = handler.generate(request);
    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }

  @Test
  public void responseShowingUsernameIsReturned() throws Exception {
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    UsersShowMiddleware usersShowMiddleware = new UsersShowMiddleware(dataStore);
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users/1")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    UsersEditMiddleware usersEditMiddleware = new UsersEditMiddleware(usersShowMiddleware);

    Handler handler = usersEditMiddleware.call(request);

    Response actualResponse = handler.generate(request);
    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}