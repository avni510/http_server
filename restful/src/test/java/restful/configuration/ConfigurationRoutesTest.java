package restful.configuration;

import core.DataStore;
import core.Handler;
import core.Router;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

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