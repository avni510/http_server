package http_server;

import http_server.configuration.ConfigurationCommandLine;
import http_server.configuration.ConfigurationRoutes;
import http_server.middleware.FinalMiddleware;
import http_server.middleware.FileMiddleware;
import http_server.middleware.RoutingMiddleware;

import java.net.ServerSocket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  private static ConfigurationCommandLine configurationCommandLine;

  public static void main(String[] args) throws Exception {
    Router router = new Router();
    configCommandLineArgs(args);
    String directoryPath = configurationCommandLine.getDirectoryName();
    configRoutes(directoryPath, router);

    ServerResponse serverResponse = setupServerResponse(directoryPath, router);

    ServerSocket serverSocket = new ServerSocket(configurationCommandLine.getPortNumber());
    ConnectionManager server = new Server(serverSocket);

    ExecutorService threadPool = Executors.newFixedThreadPool(4);
    ServerCancellationToken serverCancellationToken = new ServerCancellationToken(!threadPool.isShutdown());

    HttpServer httpServer = new HttpServer(server, serverCancellationToken, threadPool, serverResponse);
    httpServer.execute();
  }

  private static void configCommandLineArgs(String[] args){
    configurationCommandLine = new ConfigurationCommandLine();
    configurationCommandLine.parse(args);
  }

  private static void configRoutes(String directoryPath, Router router){
    ConfigurationRoutes configurationRoutes = new ConfigurationRoutes(directoryPath);
    configurationRoutes.populateRoutes(router);
  }

  private static ServerResponse setupServerResponse(String directoryPath, Router router){
    FinalMiddleware finalMiddleware = new FinalMiddleware();
    FileMiddleware fileMiddleware = new FileMiddleware(directoryPath, finalMiddleware);
    RoutingMiddleware routingMiddleware = new RoutingMiddleware(router, fileMiddleware);
    return new ServerResponse(routingMiddleware);
  }
}
