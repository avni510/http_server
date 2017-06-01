package http_server.restful;

import http_server.DataStore;
import http_server.Router;
import http_server.Server;
import http_server.ServerExecutor;
import http_server.ServerCancellationToken;
import http_server.HttpServer;

import http_server.restful.configuration.ConfigurationRoutes;
import http_server.restful.middleware.DataStoreMiddleware;

import http_server.middleware.FinalMiddleware;
import http_server.middleware.RoutingMiddleware;

import java.net.ServerSocket;

public class Main {
  private static int portNumber = 4444;

  public static void main(String[] args) throws Exception {
    DataStore dataStore = new DataStore();
    ConfigurationRoutes configurationRoutesRestful = new ConfigurationRoutes(dataStore);
    Router router = configurationRoutesRestful.buildRouter();

    ServerSocket serverSocket = new ServerSocket(portNumber);
    Server server = new Server(serverSocket);

    RoutingMiddleware app = setupApp(router, dataStore);

    ServerExecutor serverExecutor = new ServerExecutor(app);

    ServerCancellationToken serverCancellationToken = new ServerCancellationToken();
    serverCancellationToken.setListeningCondition(true);

    HttpServer httpServer = new HttpServer(server, serverExecutor, serverCancellationToken);
    httpServer.execute();
  }

  private static RoutingMiddleware setupApp(Router router, DataStore dataStore){
    FinalMiddleware finalMiddleware = new FinalMiddleware();
    DataStoreMiddleware dataStoreMiddleware = new DataStoreMiddleware(dataStore, finalMiddleware);
    return new RoutingMiddleware(router, dataStoreMiddleware);
  }
}
