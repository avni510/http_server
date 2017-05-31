package http_server.middleware;

import http_server.DataStore;
import http_server.Middleware;
import http_server.request.Request;
import http_server.request.RequestBuilder;
import http_server.request.RequestMethod;
import http_server.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataStoreMiddlewareTest {

  @Test
  public void responseForPutRequestIsReturned() throws Exception {
    Middleware nextMiddleware = new FinalMiddleware();
    DataStore dataStore = new DataStore();
    dataStore.storeEntry("1", "foo");
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.PUT)
        .setUri("/users/1")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .setBody("username=bar")
        .build();
    DataStoreMiddleware dataStoreMiddleware = new DataStoreMiddleware(dataStore, nextMiddleware);

    Response actualResponse = dataStoreMiddleware.call(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals(dataStore.getValue("1"), "bar");
  }

  @Test
  public void responseForDeleteRequestIsReturned() throws Exception {
    Middleware nextMiddleware = new FinalMiddleware();
    DataStore dataStore = new DataStore();
    dataStore.storeEntry("1", "foo");
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.DELETE)
        .setUri("/users/1")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    DataStoreMiddleware dataStoreMiddleware = new DataStoreMiddleware(dataStore, nextMiddleware);

    Response actualResponse = dataStoreMiddleware.call(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertTrue(dataStore.isStoreEmpty());
  }

  @Test
  public void errorIsReturnedIfIdDoesNotExist() throws Exception {
    Middleware nextMiddleware = new FinalMiddleware();
    DataStore dataStore = new DataStore();
    dataStore.storeEntry("1", "foo");
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.DELETE)
        .setUri("/users/500")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    DataStoreMiddleware dataStoreMiddleware = new DataStoreMiddleware(dataStore, nextMiddleware);

    Response actualResponse = dataStoreMiddleware.call(request);

    assertEquals("404 Not Found", actualResponse.getStatusCodeMessage());
  }


  @Test
  public void responseForGetRequestEditingUsernameIsReturned() throws Exception {
    Middleware nextMiddleware = new FinalMiddleware();
    DataStore dataStore = new DataStore();
    dataStore.storeEntry("1", "foo");
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users/1/edit")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    DataStoreMiddleware dataStoreMiddleware = new DataStoreMiddleware(dataStore, nextMiddleware);

    Response actualResponse = dataStoreMiddleware.call(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }

  @Test
  public void responseForGetRequestShowingUsernameIsReturned() throws Exception {
    Middleware nextMiddleware = new FinalMiddleware();
    DataStore dataStore = new DataStore();
    dataStore.storeEntry("1", "foo");
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users/1")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    DataStoreMiddleware dataStoreMiddleware = new DataStoreMiddleware(dataStore, nextMiddleware);

    Response actualResponse = dataStoreMiddleware.call(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}