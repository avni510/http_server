package http_server.configuration;

import http_server.response.Response;
import http_server.Router;
import http_server.ServerResponse;
import http_server.middleware.FileMiddleware;
import http_server.middleware.RoutingMiddleware;
import http_server.middleware.FinalMiddleware;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

public class ConfigurationRoutesTest {
  private String rootPath = System.getProperty("user.dir");

  private ServerResponse setupServerResponse(Router router){
    FinalMiddleware finalMiddleware = new FinalMiddleware();
    String rootDirectoryPath = System.getProperty("user.dir") + "/code";
    FileMiddleware fileMiddleware = new FileMiddleware(rootDirectoryPath, finalMiddleware);
    RoutingMiddleware routingMiddleware =  new RoutingMiddleware(router, fileMiddleware);
    return new ServerResponse(routingMiddleware);
  }

  private Response generateHelloWorldResponse(Router router) throws Exception {
    String request = "GET /hello_world HTTP/1.1\r\nHost: localhost\r\n\r\n";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    ServerResponse serverResponse = setupServerResponse(router);
    return serverResponse.getHttpResponse(bufferedReader);
  }

  @Test
  public void routesArePopulated() throws Exception {
    Router router = new Router();
    ConfigurationRoutes configurationRoutes = new ConfigurationRoutes(rootPath);

    configurationRoutes.populateRoutes(router);

    Response actualResponse = generateHelloWorldResponse(router);
    assertEquals("hello world", new String(actualResponse.getBody()));
  }
}