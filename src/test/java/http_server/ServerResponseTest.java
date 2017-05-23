package http_server;

import http_server.handler.directory.DirectoryGetHandler;
import http_server.handler.HelloWorldGetHandler;

import http_server.middleware.RoutingMiddleware;
import http_server.middleware.FileMiddleware;
import http_server.middleware.FinalMiddleware;

import http_server.request.RequestMethod;
import http_server.response.Response;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

public class ServerResponseTest {
  private String rootDirectoryPath;

  private String getHtmlBody() {
    return
        "<li> <a href=/image.png>" +
        "image.png</a></li>" +
        "<li> <a href=/result.txt>" +
        "result.txt</a></li>" +
        "<li> <a href=/validation.txt>" +
        "validation.txt</a></li>" +
        "<li> <a href=/log_time_entry.txt>" +
        "log_time_entry.txt</a></li>";
  }

  private void setupRouter(Router router){
    router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldGetHandler());
    router.addRoute(RequestMethod.GET, "/", new DirectoryGetHandler(rootDirectoryPath));
  }

  private RoutingMiddleware setupMiddlewares(Router router){
    FinalMiddleware finalMiddleware = new FinalMiddleware();
    String rootDirectoryPath = System.getProperty("user.dir") + "/code";
    FileMiddleware fileMiddleware = new FileMiddleware(rootDirectoryPath, finalMiddleware);
    return new RoutingMiddleware(router, fileMiddleware);
  }

  private BufferedReader getInputStream(String uri){
    String httpRequest = "GET " + uri + " HTTP/1.1\r\nHost: localhost\r\n\r\n";
    InputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes());
    return new BufferedReader(new InputStreamReader(inputStream));
  }

  @Test
  public void httpResponseIsReturned() throws Exception {
    Router router = new Router();
    setupRouter(router);
    RoutingMiddleware routingMiddleware = setupMiddlewares(router);
    ServerResponse serverResponse = new ServerResponse(routingMiddleware);

    BufferedReader bufferedReader = getInputStream("/hello_world");
    Response actualResponse = serverResponse.getHttpResponse(bufferedReader);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("hello world", new String(actualResponse.getBody()));
  }

  @Test
  public void httpResponseIsReturnedForUriDoesNotExist() throws Exception {
    Router router = new Router();
    setupRouter(router);
    RoutingMiddleware routingMiddleware = setupMiddlewares(router);
    ServerResponse serverResponse = new ServerResponse(routingMiddleware);

    BufferedReader bufferedReader = getInputStream("/nonexistent_uri");
    Response actualResponse = serverResponse.getHttpResponse(bufferedReader);

    assertEquals("404 Not Found", actualResponse.getStatusCodeMessage());
  }

  @Test
  public void htmlOfFilesIsSentAsAResponse() throws Exception {
    this.rootDirectoryPath = System.getProperty("user.dir") + "/code";
    Router router = new Router();
    setupRouter(router);
    RoutingMiddleware routingMiddleware = setupMiddlewares(router);
    ServerResponse serverResponse = new ServerResponse(routingMiddleware);

    BufferedReader bufferedReader = getInputStream("/");
    Response actualResponse = serverResponse.getHttpResponse(bufferedReader);

    assertEquals(getHtmlBody(), new String(actualResponse.getBody()));
  }

  @Test
  public void fileContentsAreSentAsAResponseForResultFile() throws Exception {
    this.rootDirectoryPath = System.getProperty("user.dir") + "/code";
    Router router = new Router();
    setupRouter(router);
    RoutingMiddleware routingMiddleware = setupMiddlewares(router);
    ServerResponse serverResponse = new ServerResponse(routingMiddleware);

    BufferedReader bufferedReader = getInputStream("/result.txt");
    Response actualResponse = serverResponse.getHttpResponse(bufferedReader);

    String fileContents = "module TimeLogger\nend\n";
    assertEquals(fileContents, new String(actualResponse.getBody()));
  }

  @Test
  public void fileContentsAreSentAsAResponseForValidationFile() throws Exception {
    Router router = new Router();
    setupRouter(router);
    RoutingMiddleware routingMiddleware = setupMiddlewares(router);
    ServerResponse serverResponse = new ServerResponse(routingMiddleware);

    BufferedReader bufferedReader = getInputStream("/validation.txt");
    Response actualResponse = serverResponse.getHttpResponse(bufferedReader);

    String fileContents = "x = 1\ny = 2\n";
    assertEquals(fileContents, new String(actualResponse.getBody()));
  }
}