package http_server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MockServer implements ConnectionManager{
  private Connection mockServerSocketConnection;

  public MockServer withAcceptStubbedToReturn(Connection mockServerSocketConnection){
    this.mockServerSocketConnection = mockServerSocketConnection;
    return this;
  }

  public Connection accept() throws IOException {
    return mockServerSocketConnection;
  }

  public void close() throws IOException {
  }
}
