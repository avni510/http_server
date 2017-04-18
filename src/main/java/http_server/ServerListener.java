package http_server;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {
  private ConnectionManager server;
  private CancellationToken serverCancellationToken;
  private Processor serverProcessor;
  private Connection serverSocketConnection;

  public ServerListener(Connection serverSocketConnection, CancellationToken serverCancellationToken, Processor serverProcessor) {
    this.serverSocketConnection = serverSocketConnection;
    this.serverCancellationToken = serverCancellationToken;
    this.serverProcessor = serverProcessor;
  }

  public void runner() throws Exception {
    while (serverCancellationToken.isListening()) {
      serverProcessor.execute(serverSocketConnection);
    }
  }
}

