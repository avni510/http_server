package http_server;

public class MockProcessor implements Processor {
  public Connection clientConnection;

  public MockProcessor() {
  }

  public void execute(Connection clientConnection, String directoryPath) throws Exception {
    this.clientConnection = clientConnection;
  }

  public boolean executeWasCalledWith(Connection clientConnection) {
    return clientConnection == this.clientConnection;
  }
}
