package http_server;

import java.io.IOException;

public class HttpServer {
  private ConnectionManager server;
  private CancellationToken serverCancellationToken;
  private Processor serverProcessor;

  public HttpServer(ConnectionManager server, CancellationToken serverCancellationToken, Processor serverProcessor) {
    this.server = server;
    this.serverCancellationToken = serverCancellationToken;
    this.serverProcessor = serverProcessor;
  }

  public void run() {
    while (serverCancellationToken.isListening()) {
      Connection serverSocketConnection = null;
      try {
        serverSocketConnection = server.accept();
        serverProcessor.setClientConnection(serverSocketConnection);
        serverProcessor.run();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          serverSocketConnection.out().close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}

