package restful.middleware;

import core.DataStore;
import core.Handler;
import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;
import core.response.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UsersDeleteRequestMiddlewareTest {

  private DataStore<Integer, String> setupDataStore(){
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    return dataStore;
  }

  private UsersPutRequestMiddleware setupPreviousMiddleware(DataStore<Integer, String> dataStore){
    UsersShowMiddleware usersShowMiddleware = new UsersShowMiddleware(dataStore);
    UsersEditMiddleware usersEditMiddleware = new UsersEditMiddleware(usersShowMiddleware);
    return new UsersPutRequestMiddleware(dataStore, usersEditMiddleware);
  }

  @Test
  public void responseForDeleteRequestIsReturned() throws Exception {
    DataStore<Integer, String> dataStore = setupDataStore();
    UsersPutRequestMiddleware app = setupPreviousMiddleware(dataStore);
    UsersDeleteRequestMiddleware usersDeleteRequestMiddleware = new UsersDeleteRequestMiddleware(dataStore, app);
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.DELETE)
        .setUri("/users/1")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();

    Handler handler = usersDeleteRequestMiddleware.call(request);

    Response actualResponse = handler.generate(request);
    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertTrue(dataStore.isStoreEmpty());
  }

  @Test
  public void responseForPutRequestIsReturned() throws Exception {
    DataStore<Integer, String> dataStore = setupDataStore();
    UsersPutRequestMiddleware app = setupPreviousMiddleware(dataStore);
    UsersDeleteRequestMiddleware usersDeleteRequestMiddleware = new UsersDeleteRequestMiddleware(dataStore, app);
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.PUT)
        .setUri("/users/1")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .setBody("username=bar")
        .build();

    Handler handler = usersDeleteRequestMiddleware.call(request);

    Response actualResponse = handler.generate(request);
    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals(dataStore.getValue(1), "bar");
  }
}