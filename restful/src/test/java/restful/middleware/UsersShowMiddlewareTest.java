package restful.middleware;

import core.DataStore;

import core.Handler;
import core.request.RequestBuilder;
import core.request.RequestMethod;
import core.request.Request;

import core.response.Response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UsersShowMiddlewareTest {

  @Test
  public void usernameIsDisplayed() throws Exception {
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users/1")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    UsersShowMiddleware usersShowMiddleware = new UsersShowMiddleware(dataStore);

    Handler handler = usersShowMiddleware.call(request);

    Response actualResponse = handler.generate(request);
    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("foo", new String(actualResponse.getBody()));
  }
}