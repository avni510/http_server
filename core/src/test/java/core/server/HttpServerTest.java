package core.server;

import core.CancellationToken;
import core.Constants;
import core.Router;

import core.handler.BaseHandler;

import core.mocks.MockServerExecutor;
import core.mocks.MockServer;
import core.mocks.MockSocket;
import core.mocks.MockServerSocketConnection;
import core.mocks.MockServerCancellationToken;

import core.request.RequestMethod;

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

  private void setupRouter() {
    router.addRoute(RequestMethod.GET, "/hello_world", new BaseHandler());
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