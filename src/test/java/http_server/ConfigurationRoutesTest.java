package http_server;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

public class ConfigurationRoutesTest {
  private String rootPath = System.getProperty("user.dir");

  private Response generateHelloWorldResponse(Router router) throws Exception {
    String request = "GET /hello_world HTTP/1.1\r\nHost: localhost\r\n\r\n";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    return router.generateHttpResponse(bufferedReader);
  }

  @Test
  public void routesArePopulated() throws Exception {
    Router router = new Router();
    ConfigurationRoutes configurationRoutes = new ConfigurationRoutes(router, rootPath);

    configurationRoutes.populateRoutes();

    Response actualResponse = generateHelloWorldResponse(router);
    assertEquals("hello world", new String(actualResponse.getBody()));
  }
}