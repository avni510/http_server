package http_server;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

public class Server implements ConnectionManager {
  private ServerSocket serverSocket;

  public Server (ServerSocket serverSocket){
    this.serverSocket = serverSocket;
  }

  public Connection accept() throws IOException {
    Socket clientSocket = serverSocket.accept();
    return new ServerSocketConnection(clientSocket);
  }

  public void close() throws IOException {
    serverSocket.close();
  }
}
