package http_server;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;

import static org.junit.Assert.*;

public class HttpServerTest {
  private CancellationToken serverCancellationToken;
  private MockProcessor processor;
  private Connection serverSocketConnection;
  private MockServer server;

  private Socket createMockSocket() {
    String request = "GET / HTTP/1.1\r\nHost: localhost\r\n\r\n";
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(request.getBytes());
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    return new MockSocket(byteArrayInputStream, byteArrayOutputStream);
  }

  @Before
  public void setUp() throws Exception {
    Socket socket = createMockSocket();
    this.serverSocketConnection = new MockServerSocketConnection(socket);
    this.server = new MockServer().withAcceptStubbedToReturn(serverSocketConnection);
    serverCancellationToken = new MockServerCancellationToken();
    this.processor = new MockProcessor();
  }


  @Test
  public void theServerStopsListening() throws Exception {
    HttpServer httpServer = new HttpServer(server, serverCancellationToken, processor);

    httpServer.run();

    assertFalse(serverCancellationToken.isListening());
  }

  @Test
  public void executeWasCalledWithASocket() throws Exception {
    HttpServer serverListener = new HttpServer(server, serverCancellationToken, processor);

    serverListener.run();

    assertTrue(processor.executeWasCalledWith(serverSocketConnection));
  }
}