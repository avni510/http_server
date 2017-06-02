package restful.handler.users;

import core.DataStore;
import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;
import core.response.Response;
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
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    UsersDeleteHandler usersDeleteHandler = new UsersDeleteHandler(dataStore);

    Response actualResponse = usersDeleteHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals(dataStore.count(), 0);
  }
}