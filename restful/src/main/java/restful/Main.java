package restful;

import core.Router;

import core.middleware.FinalMiddleware;
import core.middleware.RoutingMiddleware;

import core.server.Server;
import core.server.ServerExecutor;
import core.server.ServerCancellationToken;
import core.server.HttpServer;

import core.utils.DataStore;

import restful.configuration.ConfigurationRoutes;

import java.net.ServerSocket;

public class Main {
  private static int portNumber = 4444;

  public static void main(String[] args) throws Exception {
    DataStore<Integer, String> dataStore = new DataStore<>();
    ConfigurationRoutes configurationRoutes = new ConfigurationRoutes(dataStore);
    Router router = configurationRoutes.buildRouter();

    ServerSocket serverSocket = new ServerSocket(portNumber);
    Server server = new Server(serverSocket);

    FinalMiddleware finalMiddleware = new FinalMiddleware();
    RoutingMiddleware app = new RoutingMiddleware(router, finalMiddleware);

    ServerExecutor serverExecutor = new ServerExecutor(app);

    ServerCancellationToken serverCancellationToken = new ServerCancellationToken();
    serverCancellationToken.setListeningCondition(true);

    HttpServer httpServer = new HttpServer(server, serverExecutor, serverCancellationToken);
    httpServer.execute();
  }
}
