package http_server.mocks;

import http_server.Connection;
import http_server.IExecutorService;

public class MockThreadPoolExecutorService implements IExecutorService {
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
