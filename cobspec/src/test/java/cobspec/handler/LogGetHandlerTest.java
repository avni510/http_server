package cobspec.handler;

import core.Constants;

import core.utils.DataStore;
import core.response.Response;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import java.io.IOException;
import java.util.Base64;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LogGetHandlerTest {

  private DataStore setUpLogs() {
    DataStore dataStore = new DataStore<String, String>();
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
        .setHeader("Host: localhost")
        .build();
    LogGetHandler logGetHandler = new LogGetHandler(setUpLogs(), username, password);

    Response actualResponse = logGetHandler.generate(request);

    assertEquals("401 Unauthorized", actualResponse.getStatusCodeMessage());
    assertEquals("WWW-Authenticate: Basic realm=\"Access to Avni's Server\"" + Constants.CLRF,
        actualResponse.getHeaders());
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
        .setHeader("Authorization: Basic " + encoded)
        .build();
    LogGetHandler logGetHandler = new LogGetHandler(setUpLogs(), username, password);

    Response actualResponse = logGetHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("HEAD /requests HTTP/1.1 GET /log HTTP/1.1 PUT /these HTTP/1.1 ",
        new String(actualResponse.getBody()));
  }
}