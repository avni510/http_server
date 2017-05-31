package http_server.mocks;

import http_server.Connection;
import http_server.ServerExecutorService;

public class MockServerExecutor implements ServerExecutorService {
  private Connection connection;

  public void execute(Connection connection) {
    this.connection = connection;
  }

  public void shutdown() {
  }

  public boolean clientConnectionWasSet(Connection clientConnection) {
    return clientConnection == this.connection;
  }
}
