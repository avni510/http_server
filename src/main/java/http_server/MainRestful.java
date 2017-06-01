package http_server;

import http_server.restful.configuration.ConfigurationRoutes;
import http_server.middleware.FinalMiddleware;
import http_server.middleware.RoutingMiddleware;
import http_server.restful.middleware.DataStoreMiddleware;

import java.net.ServerSocket;

public class MainRestful {
  private static int portNumber = 4444;

  public static void main(String[] args) throws Exception {
    DataStore dataStore = new DataStore();
    ConfigurationRoutes configurationRoutes = new ConfigurationRoutes(dataStore);
    Router router = configurationRoutes.buildRouter();

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
