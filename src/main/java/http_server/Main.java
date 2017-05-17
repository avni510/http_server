package http_server;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

  public static void main(String[] args) throws Exception {
    Router router = new Router();
    ConfigurationCommandLine configurationCommandLine = new ConfigurationCommandLine();
    configurationCommandLine.parse(args);
    String directoryPath = configurationCommandLine.getDirectoryName();
    ConfigurationRoutes configurationRoutes = new ConfigurationRoutes(directoryPath);
    configurationRoutes.populateRoutes(router);

    ServerSocket serverSocket = new ServerSocket(configurationCommandLine.getPortNumber());
    ConnectionManager server = new Server(serverSocket);

    ExecutorService threadPool = Executors.newFixedThreadPool(4);
    ServerCancellationToken serverCancellationToken = new ServerCancellationToken(!threadPool.isShutdown());

    HttpServer httpServer = new HttpServer(server, serverCancellationToken, threadPool, router);
    httpServer.execute();
  }
}
