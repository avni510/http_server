package http_server;


import java.net.ServerSocket;

public class Main {

  public static void main(String[] args) throws Exception {
    Configuration configuration = new Configuration();
    configuration.parse(args);
    configuration.populateRoutes();
    ServerSocket serverSocket = new ServerSocket(configuration.getPortNumber());
    ConnectionManager server = new Server(serverSocket);
    ServerProcessor serverProcessor = new ServerProcessor();
    ServerCancellationToken serverCancellationToken = new ServerCancellationToken();
    HttpServer httpServer = new HttpServer(server, serverCancellationToken, serverProcessor);
    httpServer.run();

  }
}
