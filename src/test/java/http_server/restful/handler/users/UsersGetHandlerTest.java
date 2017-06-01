package http_server.restful.handler.users;

import http_server.DataStore;
import http_server.request.Request;
import http_server.request.RequestBuilder;
import http_server.request.RequestMethod;

import http_server.response.Response;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class UsersGetHandlerTest {

  private String htmlCreateNewUser(){
    return
        "<form action=\"/users\" method=\"post\">" +
           "Username: <br> " +
           "<input type=\"text\" name=\"username\">" +
           "<input type=\"submit\" value=\"Submit\">" +
         "</form> <br>";
  }

  private String htmlEditUser(){
    return "<form action=\"/users/" + "1" + "\">" +
          "Username: <br> " +
          "<input type=\"text\" name=\"username\">" +
          "<input type=\"submit\" value=\"Submit\">" +
        "</form> <br>";
  }

  private String htmlUsernamesTable(){
   return "<table>" +
            "<tr>" +
              "<th>id</th>" +
              "<th>username</th>" +
            "</tr>" +
            "<tr>" +
              "<td>1</td>" +
              "<td>foo</td>" +
            "</tr>" +
            "<tr>" +
               "<td>2</td>" +
               "<td>bar</td>" +
            "</tr>" +
          "</table>";
  }

  private String htmlShowUser(){
   return "<p>foo</p>";
  }

  @Test
  public void aFormIsDisplayedToCreateAUser() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users/new")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    DataStore<String, String> dataStore = new DataStore<>();
    UsersGetHandler userGetHandler = new UsersGetHandler(dataStore);

    Response actualResponse = userGetHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals(htmlCreateNewUser(), new String(actualResponse.getBody()));
  }

  @Test
  public void allUsersAreDisplayed() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    DataStore<String, String> dataStore = new DataStore<>();
    dataStore.storeEntry("1", "foo");
    dataStore.storeEntry("2", "bar");
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
    DataStore<String, String> dataStore = new DataStore<>();
    dataStore.storeEntry("1", "foo");
    UsersGetHandler userGetHandler = new UsersGetHandler(dataStore);

    Response actualResponse = userGetHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals(htmlEditUser(), new String(actualResponse.getBody()));
  }

  @Test
  public void aSpecificUsernameIsDisplayed() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users/1")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    DataStore<String, String> dataStore = new DataStore<>();
    dataStore.storeEntry("1", "foo");
    UsersGetHandler userGetHandler = new UsersGetHandler(dataStore);

    Response actualResponse = userGetHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals(htmlShowUser(), new String(actualResponse.getBody()));
  }
}