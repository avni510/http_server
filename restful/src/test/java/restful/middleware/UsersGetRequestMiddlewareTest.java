package restful.middleware;

import core.DataStore;
import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;
import core.response.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UsersGetRequestMiddlewareTest {

  @Test
  public void responseForGetRequestEditingUsernameIsReturned() throws Exception {
    DataStore<Integer, String> dataStore = new DataStore();
    dataStore.storeEntry(1, "foo");
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users/1/edit")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    UsersGetRequestMiddleware usersGetRequestMiddleware = new UsersGetRequestMiddleware(dataStore);

    Response actualResponse = usersGetRequestMiddleware.call(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }

  @Test
  public void responseForGetRequestShowingUsernameIsReturned() throws Exception {
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users/1")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    UsersGetRequestMiddleware usersGetRequestMiddleware = new UsersGetRequestMiddleware(dataStore);

    Response actualResponse = usersGetRequestMiddleware.call(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}