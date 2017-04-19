package http_server;

import org.junit.Before;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.*;

public class ServerListenerTest {
  private MockServerCancellationToken mockServerCancellationToken;
  private MockProcessor mockProcessor;
  private Connection mockServerSocketConnection;

  @Before
  public void setUp() throws Exception {
    MockServer mockServer = new MockServer();
    mockServerSocketConnection = mockServer.accept();
    mockServerCancellationToken = new MockServerCancellationToken();
    mockProcessor = mockServer.getProcessor();
    ServerListener serverListener = new ServerListener(mockServer, mockServerCancellationToken, mockProcessor);
    serverListener.runner();
  }

  @Test
  public void theServerStopsListening() throws Exception {
    assertEquals(false, mockServerCancellationToken.isListening());
  }

  @Test
  public void executeWasCalledWithASocket() throws Exception {
    assertEquals(true, mockProcessor.executeWasCalledWith(mockServerSocketConnection));
  }
}