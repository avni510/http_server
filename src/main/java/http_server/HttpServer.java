package http_server;

import java.io.IOException;

public class HttpServer {
  private ConnectionManager server;
  private CancellationToken serverCancellationToken;
  private IExecutorService threadPoolExecutorService;

  public HttpServer(ConnectionManager server, IExecutorService threadPoolExecutorService,
                    CancellationToken serverCancellationToken) {
    this.server = server;
    this.threadPoolExecutorService = threadPoolExecutorService;
    this.serverCancellationToken = serverCancellationToken;
  }

  public void execute() {
    try {
      while (serverCancellationToken.isListening()) {
        Connection connection = server.accept();
        threadPoolExecutorService.execute(connection);
      }
    }
    catch(Exception e){
      e.printStackTrace();
    } finally {
      try {
        threadPoolExecutorService.shutdown();
        server.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}

