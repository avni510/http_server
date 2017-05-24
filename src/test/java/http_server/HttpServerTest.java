package http_server;

import http_server.handler.HelloWorldGetHandler;

import http_server.middleware.FileMiddleware;
import http_server.middleware.RoutingMiddleware;
import http_server.middleware.FinalMiddleware;

import http_server.mocks.MockSocket;

import http_server.mocks.MockProcessor;
import http_server.mocks.MockServerSocketConnection;
import http_server.mocks.MockServer;
import http_server.mocks.MockServerCancellationToken;


import http_server.request.RequestMethod;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertFalse;

public class HttpServerTest {
  private CancellationToken serverCancellationToken;
  private MockProcessor serverProcessor;
  private MockServerSocketConnection serverSocketConnection;
  private MockServer server;
  private Router router;
  private ServerResponse serverResponse;

  private Socket createMockSocket(String request) {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(request.getBytes());
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    return new MockSocket(byteArrayInputStream, byteArrayOutputStream);
  }

  private void setupRouter(){
    router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldGetHandler());
  }

  private ServerResponse setupServerResponse(){
    FinalMiddleware finalMiddleware = new FinalMiddleware();
    String rootDirectoryPath = System.getProperty("user.dir") + "/code";
    FileMiddleware fileMiddleware = new FileMiddleware(rootDirectoryPath, finalMiddleware);
    RoutingMiddleware routingMiddleware =  new RoutingMiddleware(router, fileMiddleware);
    return new ServerResponse(routingMiddleware);
  }

  @Before
  public void setUp() throws Exception {
    String request = "GET /hello_world HTTP/1.1\r\nHost: localhost\r\n\r\n";
    Socket socket = createMockSocket(request);
    this.serverSocketConnection = new MockServerSocketConnection(socket);
    serverSocketConnection.setStoredInputData(request);
    this.server = new MockServer().withAcceptStubbedToReturn(serverSocketConnection);
    serverCancellationToken = new MockServerCancellationToken();
    this.serverProcessor = new MockProcessor(serverSocketConnection);
    this.router = new Router();
    setupRouter();
    this.serverResponse = setupServerResponse();
  }


  @Test
  public void theServerStopsListening() throws Exception {
    ExecutorService threadPool = Executors.newFixedThreadPool(1);
    HttpServer httpServer = new HttpServer(server, serverCancellationToken, threadPool, serverResponse);

    httpServer.execute();

    assertFalse(serverCancellationToken.isListening());
  }

//  @Test
//  public void clientConnectionIsSetup() throws Exception {
//    HttpServer serverListener = new HttpServer(server, serverCancellationToken, serverProcessor);
//
//    serverListener.execute();
//
//    assertTrue(serverProcessor.clientConnectionWasSet(serverSocketConnection));
//  }
}