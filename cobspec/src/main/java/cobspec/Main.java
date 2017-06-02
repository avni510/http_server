package cobspec;

import cobspec.configuration.ConfigurationCommandLine;
import cobspec.configuration.ConfigurationRoutes;

import core.middleware.FinalMiddleware;
import cobspec.middleware.FileMiddleware;
import core.*;
import core.middleware.RoutingMiddleware;

import java.net.ServerSocket;

public class Main {

  public static void main(String[] args) throws Exception {
    ConfigurationCommandLine configurationCommandLine = configCommandLine(args);
    String directoryPath = configurationCommandLine.getDirectoryName();
    Integer portNumber = configurationCommandLine.getPortNumber();

    Router router = configRoutes(directoryPath);

    ServerSocket serverSocket = new ServerSocket(portNumber);
    Server server = new Server(serverSocket);

    RoutingMiddleware app = setupApp(router, directoryPath);

    ServerExecutor serverExecutor = new ServerExecutor(app);

    ServerCancellationToken serverCancellationToken = new ServerCancellationToken();
    serverCancellationToken.setListeningCondition(true);

    HttpServer httpServer = new HttpServer(server, serverExecutor, serverCancellationToken);
    httpServer.execute();
  }

  private static ConfigurationCommandLine configCommandLine(String[] args){
    ConfigurationCommandLine configurationCommandLine = new ConfigurationCommandLine();
    configurationCommandLine.parse(args);
    return configurationCommandLine;
  }

  private static Router configRoutes(String directoryPath){
    ConfigurationRoutes configurationRoutes = new ConfigurationRoutes(directoryPath);
    return configurationRoutes.buildRouter();
  }

  private static RoutingMiddleware setupApp(Router router, String directoryPath){
    FinalMiddleware finalMiddleware = new FinalMiddleware();
    FileMiddleware fileMiddleware = new FileMiddleware(directoryPath, finalMiddleware);
    return new RoutingMiddleware(router, fileMiddleware);
  }
}
