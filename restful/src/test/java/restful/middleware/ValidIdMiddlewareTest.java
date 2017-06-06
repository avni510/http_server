package restful.middleware;

import core.DataStore;
import core.Handler;
import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValidIdMiddlewareTest {

  private DataStore<Integer, String> setupDataStore(){
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    return dataStore;
  }

  private UsersDeleteRequestMiddleware setupPreviousMiddlewares(DataStore<Integer, String> dataStore){
    UsersShowMiddleware usersShowMiddleware = new UsersShowMiddleware(dataStore);
    UsersEditMiddleware usersEditMiddleware = new UsersEditMiddleware(usersShowMiddleware);
    UsersPutRequestMiddleware usersPutRequestMiddleware = new UsersPutRequestMiddleware(dataStore, usersEditMiddleware);
    return new UsersDeleteRequestMiddleware(dataStore, usersPutRequestMiddleware);
  }

  @Test
  public void notFoundErrorIsReturnedIfIdIsNotInDataStore() throws Exception {
    DataStore<Integer, String> dataStore = setupDataStore();
    UsersDeleteRequestMiddleware app = setupPreviousMiddlewares(dataStore);
    ValidIdMiddleware validIdMiddleware = new ValidIdMiddleware(dataStore, app);
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.DELETE)
        .setUri("/users/500")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();

    Handler handler = validIdMiddleware.call(request);

    Response actualResponse = handler.generate(request);
    assertEquals("404 Not Found", actualResponse.getStatusCodeMessage());
  }

  @Test
  public void notFoundErrorIsReturnedIfRouteDoesNotContainId() throws Exception {
    DataStore<Integer, String> dataStore = setupDataStore();
    UsersDeleteRequestMiddleware app = setupPreviousMiddlewares(dataStore);
    ValidIdMiddleware validIdMiddleware = new ValidIdMiddleware(dataStore, app);
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.DELETE)
        .setUri("/users/foo")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();

    Handler handler = validIdMiddleware.call(request);

    Response actualResponse = handler.generate(request);
    assertEquals("404 Not Found", actualResponse.getStatusCodeMessage());
  }

  @Test
  public void responseForDeleteRequestIsReturned() throws Exception {
    DataStore<Integer, String> dataStore = setupDataStore();
    UsersDeleteRequestMiddleware app = setupPreviousMiddlewares(dataStore);
    ValidIdMiddleware validIdMiddleware = new ValidIdMiddleware(dataStore, app);
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.DELETE)
        .setUri("/users/1")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();

    Handler handler = validIdMiddleware.call(request);

    Response actualResponse = handler.generate(request);
    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}