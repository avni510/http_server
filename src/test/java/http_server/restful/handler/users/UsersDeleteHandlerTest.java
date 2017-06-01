package http_server.restful.handler.users;

import http_server.DataStore;
import http_server.request.Request;
import http_server.request.RequestBuilder;
import http_server.request.RequestMethod;
import http_server.response.Response;
import http_server.restful.handler.users.UsersDeleteHandler;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class UsersDeleteHandlerTest {

  @Test
  public void aDeleteRequestIsHandled() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.DELETE)
        .setUri("/users/1")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    DataStore dataStore = new DataStore();
    dataStore.storeEntry("1", "foo");
    UsersDeleteHandler usersDeleteHandler = new UsersDeleteHandler(dataStore);

    Response actualResponse = usersDeleteHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals(dataStore.count(), 0);
  }
}