package restful.handler.users;

import core.DataStore;
import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;
import core.response.Response;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class UsersIndexHandlerTest {

  private String htmlUsernamesTable(){
    return "{\"users\":[{\"id\":\"1\",\"username\":\"foo\"}," +
        "{\"id\":\"2\",\"username\":\"bar\"}]}";
  }

  @Test
  public void allUsersAreDisplayed() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    dataStore.storeEntry(2, "bar");
    UsersIndexHandler usersIndexHandler = new UsersIndexHandler(dataStore);

    Response actualResponse = usersIndexHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals(htmlUsernamesTable(), new String(actualResponse.getBody()));
  }
}