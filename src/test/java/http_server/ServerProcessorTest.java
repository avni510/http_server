package http_server;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ServerProcessorTest {

  @Test
  public void sendsHelloWorld() throws Exception {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("GET / HTTP/1.1\r\nHost: localhost\r\n\r\n".getBytes());
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    MockSocket mockSocket = new MockSocket(byteArrayInputStream, byteArrayOutputStream);
    MockServerSocketConnection mockServerSocketConnection = new MockServerSocketConnection(mockSocket);
    mockServerSocketConnection.setStoredInputData("GET / HTTP/1.1\r\nHost: localhost\r\n\r\n");
    ServerProcessor serverProcessor = new ServerProcessor();
    serverProcessor.execute(mockServerSocketConnection);
    assertEquals(mockServerSocketConnection.getStoredOutputData(), "HTTP/1.1 200 OK\r\n\r\n" + "hello world");
  }
}