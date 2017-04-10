package http_server;

import java.net.ServerSocket;

public class HTTPServer {

  public static void main(String args[]) throws Exception {
    ServerSocket serverSocket = new ServerSocket(4444);
    ServerListener serverListener = new ServerListener(serverSocket);
    serverListener.runner();
  }
}
