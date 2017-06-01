package http_server.core;

import http_server.cobspec.handler.HelloWorldGetHandler;

import http_server.core.mocks.MockServerExecutor;
import http_server.core.mocks.MockServer;
import http_server.core.mocks.MockSocket;
import http_server.core.mocks.MockServerSocketConnection;
import http_server.core.mocks.MockServerCancellationToken;

import http_server.core.request.RequestMethod;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.net.Socket;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class HttpServerTest {
  private CancellationToken serverCancellationToken;
  private MockServerSocketConnection serverSocketConnection;
  private MockServer server;
  private Router router;
  private MockServerExecutor serverExecutor;

  private Socket createMockSocket(String request) {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(request.getBytes());
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    return new MockSocket(byteArrayInputStream, byteArrayOutputStream);
  }

  private void setupRouter(){
    router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldGetHandler());
  }

  @Before
  public void setUp() throws Exception {
    String request = "GET /hello_world HTTP/1.1" + Constants.CLRF + "Host: localhost" +
                      Constants.CLRF + Constants.CLRF;
    Socket socket = createMockSocket(request);
    this.serverSocketConnection = new MockServerSocketConnection(socket);
    serverSocketConnection.setStoredInputData(request);
    this.server = new MockServer().withAcceptStubbedToReturn(serverSocketConnection);
    serverCancellationToken = new MockServerCancellationToken();
    this.router = new Router();
    setupRouter();
    this.serverExecutor = new MockServerExecutor();
  }


  @Test
  public void theServerStopsListening() throws Exception {
    HttpServer httpServer = new HttpServer(server, serverExecutor, serverCancellationToken);

    httpServer.execute();

    assertFalse(serverCancellationToken.isListening());
  }

  @Test
  public void clientConnectionIsSetup() throws Exception {
    HttpServer httpServer = new HttpServer(server, serverExecutor, serverCancellationToken);

    httpServer.execute();

    assertTrue(serverExecutor.clientConnectionWasSet(serverSocketConnection));
  }
}