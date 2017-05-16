package http_server;

public class MockProcessor implements Processor {
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
