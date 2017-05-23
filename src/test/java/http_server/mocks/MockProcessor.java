package http_server.mocks;

public class MockProcessor implements Runnable {
  public MockServerSocketConnection clientConnection;

  public MockProcessor(MockServerSocketConnection clientConnection) {
    this.clientConnection = clientConnection;
  }

  public boolean clientConnectionWasSet(MockServerSocketConnection clientConnection) {
    return clientConnection == this.clientConnection;
  }

  public void run() {
  }
}
