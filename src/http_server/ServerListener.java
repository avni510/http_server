package http_server;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {
  private ConnectionManager serverSocket;
  private CancellationToken serverCancellationToken;

  public ServerListener(ConnectionManager serverSocket, CancellationToken serverCancellationToken) {
    this.serverSocket = serverSocket;
    this.serverCancellationToken = serverCancellationToken;
  }

  public void runner() throws Exception {
    while (serverCancellationToken.isListening()) {
      Socket clientSocket = serverSocket.accept();
      ServerSocketConnection serverSocketConnection = new ServerSocketConnection(clientSocket);
      ServerProcessor serverProcessor = new ServerProcessor(serverSocketConnection);
      serverProcessor.execute();
    }
  }
}

