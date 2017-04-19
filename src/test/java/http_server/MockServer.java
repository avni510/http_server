package http_server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MockServer implements ConnectionManager{
  private MockProcessor mockProcessor;

  public Connection accept() throws IOException {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("GET / HTTP/1.1\r\nHost: localhost\r\n\r\n".getBytes());
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    Socket mockSocket = new MockSocket(byteArrayInputStream, byteArrayOutputStream);
    MockServerSocketConnection mockServerSocketConnection = new MockServerSocketConnection(mockSocket);
    mockProcessor = new MockProcessor(mockServerSocketConnection);
    return mockServerSocketConnection;
  }

  public MockProcessor getProcessor(){
    return mockProcessor;
  }
}
