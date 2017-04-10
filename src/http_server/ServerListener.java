package http_server;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {
  private ServerSocket serverSocket;

  public ServerListener(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
  }

  public void runner() throws Exception {
    System.out.println("Listening for connection on port 4444");
    boolean listening = true;
    while (listening) {
      Socket clientSocket = serverSocket.accept();
      ServerSocketConnection serverSocketConnection = new ServerSocketConnection(clientSocket);
      ServerProcessor serverProcessor = new ServerProcessor(serverSocketConnection);
      serverProcessor.execute();
    }
    serverSocket.close();
  }
}

