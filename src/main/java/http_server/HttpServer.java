package http_server;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
  private ConnectionManager server;
  private CancellationToken serverCancellationToken;
  private ExecutorService threadPool;

  public HttpServer(ConnectionManager server, CancellationToken serverCancellationToken) {
    this.server = server;
    this.serverCancellationToken = serverCancellationToken;
    this.threadPool = Executors.newFixedThreadPool(4);
  }

  public void execute() {
    try {
      while (serverCancellationToken.isListening() || !threadPool.isShutdown()) {
        threadPool.execute(new ServerProcessor(server.accept()));
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

