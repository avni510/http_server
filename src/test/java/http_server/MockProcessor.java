package http_server;

public class MockProcessor implements Processor {
  public Connection clientConnection;

  public void setClientConnection(Connection clientConnection) {
    this.clientConnection = clientConnection;
  }

  public boolean clientConnectionWasSet(Connection clientConnection) {
    return clientConnection == this.clientConnection;
  }

  public void run() {
  }
}
