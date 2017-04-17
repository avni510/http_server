package http_server;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {
  private ConnectionManager server;
  private CancellationToken serverCancellationToken;
  private Processor serverProcessor;

  public ServerListener(ConnectionManager server, CancellationToken serverCancellationToken, Processor serverProcessor) {
    this.server = server;
    this.serverCancellationToken = serverCancellationToken;
    this.serverProcessor = serverProcessor;
  }

  public void runner() throws Exception {
    while (serverCancellationToken.isListening()) {
      Socket clientSocket = server.accept();
      ServerSocketConnection serverSocketConnection = new ServerSocketConnection(clientSocket);
      ServerProcessor serverProcessor = new ServerProcessor();
      serverProcessor.execute(serverSocketConnection);
    }
  }
}

