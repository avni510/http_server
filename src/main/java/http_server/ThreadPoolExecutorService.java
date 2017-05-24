package http_server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExecutorService implements IExecutorService{
  private Middleware app;
  private CancellationToken cancellationToken;
  private ExecutorService threadPool;

  public ThreadPoolExecutorService (Middleware app,
                                    CancellationToken cancellationToken) {
    this.app = app;
    this.cancellationToken = cancellationToken;
  }

  public void execute(Connection connection) {
    threadPool = Executors.newFixedThreadPool(4);
    threadPool.execute(new ServerProcessor(connection, app));
    this.cancellationToken.setListeningCondition(!threadPool.isShutdown());
  }

  public void shutdown(){
    threadPool.shutdown();
  }
}
