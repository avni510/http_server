package http_server;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.*;

public class ServerListenerTest {
  private MockServerCancellationToken mockServerCancellationToken;
  private MockProcessor mockProcessor;
  private MockServerSocketConnection mockServerSocketConnection;

  @Before
  public void setUp() throws Exception {
    ConnectionManager mockServer = new MockServer();
    Socket clientConnection = mockServer.accept();
    mockServerSocketConnection = new MockServerSocketConnection(clientConnection);
    mockServerCancellationToken = new MockServerCancellationToken();
    mockProcessor = new MockProcessor();
    ServerListener serverListener = new ServerListener(mockServerSocketConnection, mockServerCancellationToken, mockProcessor);
    serverListener.runner();
  }

  @Test
  public void theServerStopsListening() throws Exception {
    assertEquals(false, mockServerCancellationToken.isListening());
  }

  @Test
  public void executeWasCalledWithASocket() throws Exception {
    MockServer mockServer = new MockServer();
    assertEquals(true, mockProcessor.executeWasCalledWith(mockServerSocketConnection));
  }
}