package http_server.restful.configuration;

import http_server.DataStore;
import http_server.Handler;
import http_server.Router;

import http_server.request.Request;
import http_server.request.RequestBuilder;
import http_server.request.RequestMethod;

import http_server.response.Response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigurationRoutesTest {

  @Test
  public void routesArePopulated() throws Exception {
    DataStore<Integer, String> dataStore = new DataStore<>();
    ConfigurationRoutes configurationRoutes = new ConfigurationRoutes(dataStore);

    Router router = configurationRoutes.buildRouter();

    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    Handler handler = router.retrieveHandler(request.getRequestMethod(), request.getUri());
    Response actualResponse = handler.generate(request);
    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}