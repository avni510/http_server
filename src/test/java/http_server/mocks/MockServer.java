package http_server.mocks;

import http_server.Connection;
import http_server.ConnectionManager;

import java.io.IOException;

public class MockServer implements ConnectionManager {
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