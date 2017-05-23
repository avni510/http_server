package http_server;

import java.io.IOException;

import java.util.concurrent.ExecutorService;

public class HttpServer {
  private ConnectionManager server;
  private CancellationToken serverCancellationToken;
  private ExecutorService threadPool;
  private ServerResponse serverResponse;

  public HttpServer(ConnectionManager server, CancellationToken serverCancellationToken,
                    ExecutorService threadPool, ServerResponse serverResponse) {
    this.server = server;
    this.serverCancellationToken = serverCancellationToken;
    this.threadPool = threadPool;
    this.serverResponse = serverResponse;
  }

  public void execute() {
    try {
      while (serverCancellationToken.isListening()) {
        threadPool.execute(new ServerProcessor(server.accept(), serverResponse));
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

