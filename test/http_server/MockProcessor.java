package http_server;

public class MockProcessor implements Processor {
  private Connection clientConnection;

  public void execute(Connection clientConnection) throws Exception {
    this.clientConnection = clientConnection;
  }

  public boolean executeWasCalledWith(MockServerSocketConnection mockServerSocketConnection) {
    return clientConnection == mockServerSocketConnection;
  }
}
