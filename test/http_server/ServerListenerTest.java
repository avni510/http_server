package http_server;

import com.sun.xml.internal.ws.org.objectweb.asm.ByteVector;
import org.junit.Test;
import static org.junit.Assert.*;

public class ServerListenerTest {

  @Test
  public void theServerStopsListening() throws Exception {
    ConnectionManager mockServer = new MockServer();
    CancellationToken mockServerCancellationToken = new MockServerCancellationToken();
    Processor mockProcessor = new MockProcessor();
    ServerListener serverListener = new ServerListener(mockServer, mockServerCancellationToken, mockProcessor);
    serverListener.runner();
    assertEquals(false, mockServerCancellationToken.isListening());
  }

//  @Test
//  public void executeWasCalledWithASocket throws Exception {
//    ConnectionManager mockServer = new MockServer();
//    CancellationToken mockServerCancellationToken = new MockServerCancellationToken();
//    Processor mockProcessor = new MockProcessor();
//    ServerListener serverListener = new ServerListener(mockServer, mockServerCancellationToken, mockProcessor);
//    serverListener.runner();
//    MockServerSocketConnection mockServerSocketConnection = new MockServerSocketConnection(mockServer.accept());
//    assertEquals(true, mockProcessor.executeWasCalledWith(mockServerSocketConnection));
//  }
}