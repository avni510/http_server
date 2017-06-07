package core.server;

import core.Connection;
import core.Middleware;

import core.ServerExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerExecutor implements ServerExecutorService {
  private Middleware app;
  private ExecutorService threadPool;

  public ServerExecutor(Middleware app) {
    this.app = app;
  }

  public void execute(Connection connection) {
    threadPool = Executors.newFixedThreadPool(4);
    threadPool.execute(new ServerProcessor(connection, app));
  }

  public void shutdown() {
    threadPool.shutdown();
  }
}
