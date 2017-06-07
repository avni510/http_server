package restful.handler.users;

import core.DataStore;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShowUsersHandlerTest {

  @Test
  public void aSpecificUsernameIsDisplayed() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users/1")
                .setHeader("Host: localhost")
        .build();
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    ShowUsersHandler showUsersHandler = new ShowUsersHandler(dataStore);

    Response actualResponse = showUsersHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("foo", new String(actualResponse.getBody()));
  }

  @Test
  public void idInUriDoesNotExistInDataStore() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users/500")
                .setHeader("Host: localhost")
        .build();
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    ShowUsersHandler showUsersHandler = new ShowUsersHandler(dataStore);

    Response actualResponse = showUsersHandler.generate(request);

    assertEquals("404 Not Found", actualResponse.getStatusCodeMessage());
  }
}

