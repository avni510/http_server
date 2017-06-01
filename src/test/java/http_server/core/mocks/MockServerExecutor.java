package http_server.core.mocks;

import http_server.core.Connection;
import http_server.core.ServerExecutorService;

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
