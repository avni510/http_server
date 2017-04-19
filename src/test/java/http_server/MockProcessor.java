package http_server;

public class MockProcessor implements Processor {
  public Connection clientConnection;

  public MockProcessor(Connection clientConnection) {
    this.clientConnection = clientConnection;
  }

  public void execute(Connection clientConnection) throws Exception {
  }

  public boolean executeWasCalledWith(Connection mockServerSocketConnection) {
    return clientConnection.getClass().equals(mockServerSocketConnection.getClass());
  }
}
