package http_server;

import com.sun.xml.internal.ws.org.objectweb.asm.ByteVector;
import org.junit.Test;
import static org.junit.Assert.*;

public class ServerListenerTest {

  @Test
  public void theServerStopsListening() throws Exception {
    ConnectionManager mockServerSocket = new MockServer();
    CancellationToken mockServerCancellationToken = new MockServerCancellationToken();
    ServerListener serverListener = new ServerListener(mockServerSocket, mockServerCancellationToken);
    serverListener.runner();
    assertEquals(false, mockServerCancellationToken.isListening());
  }
}