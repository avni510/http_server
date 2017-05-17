package http_server;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

  public static void main(String[] args) throws Exception {
    Router router = new Router();
    Configuration configuration = new Configuration(router);
    configuration.parse(args);
    configuration.populateRoutes();

    ServerSocket serverSocket = new ServerSocket(configuration.getPortNumber());
    ConnectionManager server = new Server(serverSocket);

    ExecutorService threadPool = Executors.newFixedThreadPool(4);
    ServerCancellationToken serverCancellationToken = new ServerCancellationToken(!threadPool.isShutdown());

    HttpServer httpServer = new HttpServer(server, serverCancellationToken, threadPool, router);
    httpServer.execute();
  }
}
