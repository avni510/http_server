package core.server;

import core.CancellationToken;
import core.Connection;
import core.ConnectionManager;
import core.ServerExecutorService;

import java.io.IOException;

public class HttpServer {
  private ConnectionManager server;
  private CancellationToken serverCancellationToken;
  private ServerExecutorService serverExecutor;

  public HttpServer(ConnectionManager server, ServerExecutorService serverExecutor,
                    CancellationToken serverCancellationToken) {
    this.server = server;
    this.serverExecutor = serverExecutor;
    this.serverCancellationToken = serverCancellationToken;
  }

  public void execute() {
    try {
      while (serverCancellationToken.isListening()) {
        Connection connection = server.accept();
        serverExecutor.execute(connection);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        serverExecutor.shutdown();
        server.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}

