package http_server.restful.middleware;

import http_server.DataStore;

import http_server.request.Request;
import http_server.request.RequestBuilder;
import http_server.request.RequestMethod;

import http_server.response.Response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValidIdMiddlewareTest {

  private DataStore setupDataStore(){
    DataStore dataStore = new DataStore();
    dataStore.storeEntry("1", "foo");
    return dataStore;
  }

  private UsersDeleteRequestMiddleware setupPreviousMiddlewares(DataStore dataStore){
    UsersGetRequestMiddleware usersGetRequestMiddleware = new UsersGetRequestMiddleware(dataStore);
    UsersPutRequestMiddleware usersPutRequestMiddleware = new UsersPutRequestMiddleware(dataStore, usersGetRequestMiddleware);
    return new UsersDeleteRequestMiddleware(dataStore, usersPutRequestMiddleware);
  }

  @Test
  public void notFoundErrorIsReturnedIfIdIsNotInDataStore() throws Exception {
    DataStore dataStore = setupDataStore();
    UsersDeleteRequestMiddleware app = setupPreviousMiddlewares(dataStore);
    ValidIdMiddleware validIdMiddleware = new ValidIdMiddleware(dataStore, app);
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.DELETE)
        .setUri("/users/500")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();

    Response actualResponse = validIdMiddleware.call(request);

    assertEquals("404 Not Found", actualResponse.getStatusCodeMessage());
  }

  @Test
  public void notFoundErrorIsReturnedIfRouteDoesNotContainId() throws Exception {
    DataStore dataStore = setupDataStore();
    UsersDeleteRequestMiddleware app = setupPreviousMiddlewares(dataStore);
    ValidIdMiddleware validIdMiddleware = new ValidIdMiddleware(dataStore, app);
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.DELETE)
        .setUri("/users/foo")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();

    Response actualResponse = validIdMiddleware.call(request);

    assertEquals("404 Not Found", actualResponse.getStatusCodeMessage());
  }

  @Test
  public void responseForDeleteRequestIsReturned() throws Exception {
    DataStore dataStore = setupDataStore();
    UsersDeleteRequestMiddleware app = setupPreviousMiddlewares(dataStore);
    ValidIdMiddleware validIdMiddleware = new ValidIdMiddleware(dataStore, app);
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.DELETE)
        .setUri("/users/1")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();

    Response actualResponse = validIdMiddleware.call(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}