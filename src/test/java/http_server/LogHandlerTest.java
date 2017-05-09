package http_server;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import static org.junit.Assert.*;

public class LogHandlerTest {

  private DataStore setUpLogs() {
    DataStore dataStore = new DataStore();
    dataStore.storeEntry("HEAD", "/requests HTTP/1.1");
    dataStore.storeEntry("GET", "/log HTTP/1.1");
    dataStore.storeEntry("PUT", "/these HTTP/1.1");
    return dataStore;
  }

  @Test
  public void clientIsNotAuthenticated() throws IOException {
    String username = "admin";
    String password = "hunter2";
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/log")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .build();
    LogHandler logHandler = new LogHandler(setUpLogs(), username, password);

    Response actualResponse = logHandler.generate(request);

    assertEquals("401 Unauthorized", actualResponse.getStatusCodeMessage());
    assertEquals("WWW-Authenticate: Basic\r\nrealm=: \"Access to Avni's Server\"\r\n", actualResponse.getHeaders());
  }

  @Test
  public void clientRequestsAuthentication() throws IOException {
    String username = "admin";
    String password = "hunter2";
    String authenticationToken = username + ":" + password;
    String encoded = Base64.getEncoder().encodeToString(authenticationToken.getBytes());
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/log")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Authorization: Basic " + encoded)))
        .build();
    LogHandler logHandler = new LogHandler(setUpLogs(), username, password);

    Response actualResponse = logHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("HEAD /requests HTTP/1.1 GET /log HTTP/1.1 PUT /these HTTP/1.1 ", new String(actualResponse.getBody()));
  }
}