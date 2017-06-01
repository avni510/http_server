package http_server.restful.middleware;

import http_server.DataStore;
import http_server.request.Request;
import http_server.request.RequestBuilder;
import http_server.request.RequestMethod;

import http_server.response.Response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UsersPutRequestMiddlewareTest {

  @Test
  public void responseForPutRequestIsReturned() throws Exception {
    DataStore<String, String> dataStore = new DataStore<>();
    dataStore.storeEntry("1", "foo");
    UsersGetRequestMiddleware app = new UsersGetRequestMiddleware(dataStore);
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
    assertEquals(dataStore.getValue("1"), "bar");
  }

  @Test
  public void responseForGetRequestEditingUsernameIsReturned() throws Exception {
    DataStore<String, String> dataStore = new DataStore<>();
    dataStore.storeEntry("1", "foo");
    UsersGetRequestMiddleware app = new UsersGetRequestMiddleware(dataStore);
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