package restful.handler.users;

import core.DataStore;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

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
    DataStore<Integer, String> dataStore = new DataStore<>();
    UsersPostHandler usersPostHandler = new UsersPostHandler(dataStore);

    Response actualResponse = usersPostHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals(dataStore.getValue(1), "foo");
  }
}