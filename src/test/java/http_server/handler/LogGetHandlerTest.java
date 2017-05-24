package http_server.handler;

import http_server.response.Response;

import http_server.request.Request;
import http_server.request.RequestBuilder;
import http_server.request.RequestMethod;

import http_server.DataStore;

import org.junit.Test;

import java.io.IOException;
import java.util.Base64;

import static org.junit.Assert.assertEquals;

public class LogGetHandlerTest {

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
        .setHeader("Host: localhost")
        .build();
    LogGetHandler logGetHandler = new LogGetHandler(setUpLogs(), username, password);

    Response actualResponse = logGetHandler.generate(request);

    assertEquals("401 Unauthorized", actualResponse.getStatusCodeMessage());
    assertEquals("WWW-Authenticate: Basic realm=\"Access to Avni's Server\"\r\n", actualResponse.getHeaders());
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
        .setHeader("Authorization: Basic " + encoded)
        .build();
    LogGetHandler logGetHandler = new LogGetHandler(setUpLogs(), username, password);

    Response actualResponse = logGetHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("HEAD /requests HTTP/1.1 GET /log HTTP/1.1 PUT /these HTTP/1.1 ", new String(actualResponse.getBody()));
  }
}