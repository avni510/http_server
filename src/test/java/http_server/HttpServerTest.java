package http_server;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

public class HttpServerTest {
  private CancellationToken serverCancellationToken;
  private MockProcessor serverProcessor;
  private MockServerSocketConnection serverSocketConnection;
  private MockServer server;

  private Socket createMockSocket(String request) {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(request.getBytes());
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    return new MockSocket(byteArrayInputStream, byteArrayOutputStream);
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
  }


  @Test
  public void theServerStopsListening() throws Exception {
    ExecutorService threadPool = Executors.newFixedThreadPool(1);
    HttpServer httpServer = new HttpServer(server, serverCancellationToken, threadPool);

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