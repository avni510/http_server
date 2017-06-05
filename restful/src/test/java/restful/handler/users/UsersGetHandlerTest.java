package restful.handler.users;

import core.DataStore;
import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class UsersGetHandlerTest {

  private String htmlUsernamesTable(){
    return "{\"users\":[{\"id\":\"1\",\"username\":\"foo\"}," +
           "{\"id\":\"2\",\"username\":\"bar\"}]}";
  }

  private String htmlShowUser(){
    return "foo";
  }

  @Test
  public void aFormIsDisplayedToCreateAUser() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users/new")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    DataStore<Integer, String> dataStore = new DataStore<>();
    UsersGetHandler userGetHandler = new UsersGetHandler(dataStore);

    Response actualResponse = userGetHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
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
    UsersGetHandler userGetHandler = new UsersGetHandler(dataStore);

    Response actualResponse = userGetHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals(htmlUsernamesTable(), new String(actualResponse.getBody()));
  }

  @Test
  public void aFormIsDisplayedToEditAUsername() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users/1/edit")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    UsersGetHandler userGetHandler = new UsersGetHandler(dataStore);

    Response actualResponse = userGetHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }

  @Test
  public void aSpecificUsernameIsDisplayed() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users/1")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    DataStore<Integer, String> dataStore = new DataStore<>();
    dataStore.storeEntry(1, "foo");
    UsersGetHandler userGetHandler = new UsersGetHandler(dataStore);

    Response actualResponse = userGetHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals(htmlShowUser(), new String(actualResponse.getBody()));
  }
}