package http_server;

import static org.junit.Assert.assertEquals;

import http_server.handler.HelloWorldGetHandler;

import http_server.middleware.FinalMiddleware;
import http_server.middleware.FileMiddleware;
import http_server.middleware.RoutingMiddleware;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.net.Socket;

public class ServerProcessorTest {

  private Socket createMockSocket(String request){
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(request.getBytes());
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    return new MockSocket(byteArrayInputStream, byteArrayOutputStream);
  }

  private void setupRouter(Router router){
    router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldGetHandler());
  }

  private ServerResponse setupServerResponse(Router router){
    FinalMiddleware finalMiddleware = new FinalMiddleware();
    String rootDirectoryPath = System.getProperty("user.dir") + "/code";
    FileMiddleware fileMiddleware = new FileMiddleware(rootDirectoryPath, finalMiddleware);
    RoutingMiddleware routingMiddleware =  new RoutingMiddleware(router, fileMiddleware);
    return new ServerResponse(routingMiddleware);
  }

  @Test
  public void responseIsWrittenOutForValidRequest() throws Exception {
    String request = "GET /hello_world HTTP/1.1\r\nHost: localhost\r\n\r\n";
    Socket socket = createMockSocket(request);
    MockServerSocketConnection serverSocketConnection = new MockServerSocketConnection(socket);
    serverSocketConnection.setStoredInputData(request);
    Router router = new Router();
    setupRouter(router);
    ServerResponse serverResponse = setupServerResponse(router);
    ServerProcessor serverProcessor = new ServerProcessor(serverSocketConnection, serverResponse);

    serverProcessor.run();

    String response = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n" + "hello world";
    assertEquals(response, serverSocketConnection.getStoredOutputData());
  }

  @Test
  public void responseIsWrittenOutForInvalidRequest() throws Exception {
    String request = "/ HTTP/1.1\r\nHost: localhost\r\n\r\n";
    Socket socket = createMockSocket(request);
    MockServerSocketConnection serverSocketConnection = new MockServerSocketConnection(socket);
    serverSocketConnection.setStoredInputData(request);
    Router router = new Router();
    setupRouter(router);
    ServerResponse serverResponse = setupServerResponse(router);
    ServerProcessor serverProcessor = new ServerProcessor(serverSocketConnection, serverResponse);

    serverProcessor.run();

    String response = "HTTP/1.1 400 Bad Request\r\n\r\n";
    assertEquals(response, serverSocketConnection.getStoredOutputData());
  }
}