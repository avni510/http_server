package http_server;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

public class HttpServer {
  private ConnectionManager server;
  private CancellationToken serverCancellationToken;
  private ExecutorService threadPool;
  private Router router;

  public HttpServer(ConnectionManager server, CancellationToken serverCancellationToken, ExecutorService threadPool, Router router) {
    this.server = server;
    this.serverCancellationToken = serverCancellationToken;
    this.threadPool = threadPool;
    this.router = router;
  }

  public void execute() {
    try {
      while (serverCancellationToken.isListening()) {
        threadPool.execute(new ServerProcessor(server.accept(), router));
      }
    }
    catch(Exception e){
      e.printStackTrace();
    } finally {
      try {
        threadPool.shutdown();
        server.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}

