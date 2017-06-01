package http_server.restful.middleware;

import http_server.DataStore;
import http_server.request.Request;
import http_server.request.RequestBuilder;
import http_server.request.RequestMethod;
import http_server.response.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UsersDeleteRequestMiddlewareTest {

  private DataStore setupDataStore(){
    DataStore dataStore = new DataStore();
    dataStore.storeEntry("1", "foo");
    return dataStore;
  }

  private UsersPutRequestMiddleware setupPreviousMiddleware(DataStore dataStore){
    UsersGetRequestMiddleware usersGetRequestMiddleware = new UsersGetRequestMiddleware(dataStore);
    return new UsersPutRequestMiddleware(dataStore, usersGetRequestMiddleware);
  }

  @Test
  public void responseForDeleteRequestIsReturned() throws Exception {
    DataStore dataStore = setupDataStore();
    UsersPutRequestMiddleware app = setupPreviousMiddleware(dataStore);
    UsersDeleteRequestMiddleware usersDeleteRequestMiddleware = new UsersDeleteRequestMiddleware(dataStore, app);
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.DELETE)
        .setUri("/users/1")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();

    Response actualResponse = usersDeleteRequestMiddleware.call(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertTrue(dataStore.isStoreEmpty());
  }

  @Test
  public void responseForPutRequestIsReturned() throws Exception {
    DataStore dataStore = setupDataStore();
    UsersPutRequestMiddleware app = setupPreviousMiddleware(dataStore);
    UsersDeleteRequestMiddleware usersDeleteRequestMiddleware = new UsersDeleteRequestMiddleware(dataStore, app);
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.PUT)
        .setUri("/users/1")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .setBody("username=bar")
        .build();

    Response actualResponse = usersDeleteRequestMiddleware.call(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals(dataStore.getValue("1"), "bar");
  }
}