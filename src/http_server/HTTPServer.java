package http_server;

import java.net.ServerSocket;

public class HTTPServer {

  public static void main(String args[]) throws Exception {
    ServerSocket serverSocket = new ServerSocket(4444);
    ConnectionManager server = new Server (serverSocket);
    ServerCancellationToken serverCancellationToken = new ServerCancellationToken();
    ServerListener serverListener = new ServerListener(server, serverCancellationToken);
    serverListener.runner();
  }
}
