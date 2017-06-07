package cobspec.configuration;

import core.Handler;
import core.Router;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigurationRoutesTest {
  private String rootPath = System.getProperty("user.dir");

  @Test
  public void routesArePopulated() throws Exception {
    ConfigurationRoutes configurationRoutes = new ConfigurationRoutes(rootPath);

    Router router = configurationRoutes.buildRouter();

    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/hello_world")
        .setHeader("Host: localhost")
        .build();
    Handler handler = router.retrieveHandler(request.getRequestMethod(), request.getUri());
    Response actualResponse = handler.generate(request);
    assertEquals("hello world", new String(actualResponse.getBody()));
  }
}