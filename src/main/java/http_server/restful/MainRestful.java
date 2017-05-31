package http_server;

import http_server.configuration.ConfigurationRoutesRestful;
import http_server.middleware.DataStoreMiddleware;
import http_server.middleware.FinalMiddleware;
import http_server.middleware.RoutingMiddleware;

import java.net.ServerSocket;

public class MainRestful {
  private static int portNumber = 4444;

  public static void main(String[] args) throws Exception {
    DataStore dataStore = new DataStore();
    ConfigurationRoutesRestful configurationRoutesRestful = new ConfigurationRoutesRestful(dataStore);
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
