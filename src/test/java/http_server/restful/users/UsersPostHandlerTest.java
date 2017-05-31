package http_server.restful.users;

import http_server.DataStore;

import http_server.request.Request;
import http_server.request.RequestBuilder;
import http_server.request.RequestMethod;

import http_server.response.Response;
import http_server.restful.handler.users.UsersPostHandler;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class UsersPostHandlerTest {

  @Test
  public void aPostRequestIsHandled() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.POST)
        .setUri("/users/create")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .setBody("username=foo")
        .build();
    DataStore dataStore = new DataStore();
    UsersPostHandler usersPostHandler = new UsersPostHandler(dataStore);

    Response actualResponse = usersPostHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals(dataStore.getValue("1"), "foo");
  }
}