package http_server;

import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {

  public static void main(String args[]) throws Exception {
    ServerSocket serverSocket = new ServerSocket(4444);
    ConnectionManager server = new Server (serverSocket);
    ServerCancellationToken serverCancellationToken = new ServerCancellationToken();
    Processor serverProcessor = new ServerProcessor();
    Socket clientSocket = server.accept();
    ServerSocketConnection serverSocketConnection = new ServerSocketConnection(clientSocket) ;
    ServerListener serverListener = new ServerListener(serverSocketConnection, serverCancellationToken, serverProcessor);
    serverListener.runner();
  }
}
