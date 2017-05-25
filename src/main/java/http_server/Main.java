package http_server;

import http_server.configuration.ConfigurationCommandLine;
import http_server.configuration.ConfigurationRoutes;

import http_server.middleware.FinalMiddleware;
import http_server.middleware.FileMiddleware;
import http_server.middleware.RoutingMiddleware;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
  private static ConfigurationCommandLine configurationCommandLine;
  private static Server server;
  private static Router router;
  private static String directoryPath;
  private static ServerCancellationToken serverCancellationToken;
  private static RoutingMiddleware app;

  public static void main(String[] args) throws Exception {
    config(args);

    initialize();

    ThreadPoolExecutorService threadPoolExecutorService = new ThreadPoolExecutorService(app, serverCancellationToken);

    HttpServer httpServer = new HttpServer(server, threadPoolExecutorService, serverCancellationToken);
    httpServer.execute();
  }

  private static void config(String[] args){
    router = new Router();
    configCommandLineArgs(args);
    directoryPath = configurationCommandLine.getDirectoryName();
    configRoutes();
  }

  private static void configCommandLineArgs(String[] args){
    configurationCommandLine = new ConfigurationCommandLine();
    configurationCommandLine.parse(args);
  }

  private static void configRoutes(){
    ConfigurationRoutes configurationRoutes = new ConfigurationRoutes(directoryPath);
    configurationRoutes.populateRoutes(router);
  }

  private static void initialize() throws IOException {
    ServerSocket serverSocket = new ServerSocket(configurationCommandLine.getPortNumber());
    server = new Server(serverSocket);
    serverCancellationToken = new ServerCancellationToken();
    app = setupFirstMiddleware(directoryPath, router);
    serverCancellationToken.setListeningCondition(true);
  }

  private static RoutingMiddleware setupFirstMiddleware(String directoryPath, Router router){
    FinalMiddleware finalMiddleware = new FinalMiddleware();
    FileMiddleware fileMiddleware = new FileMiddleware(directoryPath, finalMiddleware);
    return new RoutingMiddleware(router, fileMiddleware);
  }
}
