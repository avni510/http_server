package core.server;

import core.Connection;
import core.Middleware;

import core.ServerExecutorService;

import java.util.concurrent.ExecutorService;

public class ServerExecutor implements ServerExecutorService {
  private Middleware app;
  private ExecutorService threadPool;

  public ServerExecutor(Middleware app, ExecutorService threadPool) {
    this.app = app;
    this.threadPool = threadPool;
  }

  public void execute(Connection connection) {
    threadPool.execute(new ServerProcessor(connection, app));
  }

  public void shutdown() {
    threadPool.shutdown();
  }
}
