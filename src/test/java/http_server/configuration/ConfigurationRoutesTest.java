package http_server.configuration;

import http_server.Handler;
import http_server.Router;

import http_server.request.Request;
import http_server.request.RequestBuilder;
import http_server.request.RequestMethod;

import http_server.response.Response;

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
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    Handler handler = router.retrieveHandler(request.getRequestMethod(), request.getUri());
    Response actualResponse = handler.generate(request);
    assertEquals("hello world", new String(actualResponse.getBody()));
  }
}