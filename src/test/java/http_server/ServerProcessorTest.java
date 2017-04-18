package http_server;

import static org.junit.Assert.*;
import org.junit.Test;

public class ServerProcessorTest {

  @Test
  public void sendsHelloWorld() throws Exception {
    MockServerSocketConnection mockServerSocketConnection = new MockServerSocketConnection();
    mockServerSocketConnection.setStoredInputData("GET / HTTP/1.1\r\nHost: localhost\r\n\r\n");
    ServerProcessor serverProcessor = new ServerProcessor();
    serverProcessor.execute(mockServerSocketConnection);
    assertEquals(mockServerSocketConnection.getStoredOutputData(),"HTTP/1.1 200 OK\r\n\r\n" + "hello world");
  }
}