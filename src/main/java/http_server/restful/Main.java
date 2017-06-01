package http_server.restful;

import http_server.core.Router;
import http_server.core.Server;
import http_server.core.ServerExecutor;
import http_server.core.ServerCancellationToken;
import http_server.core.HttpServer;
import http_server.core.DataStore;

import http_server.restful.configuration.ConfigurationRoutes;

import http_server.restful.middleware.UsersDeleteRequestMiddleware;
import http_server.restful.middleware.UsersPutRequestMiddleware;
import http_server.restful.middleware.UsersGetRequestMiddleware;
import http_server.restful.middleware.ValidIdMiddleware;

import http_server.core.middleware.RoutingMiddleware;

import java.net.ServerSocket;

public class Main {
  private static int portNumber = 4444;

  public static void main(String[] args) throws Exception {
    DataStore<Integer, String> dataStore = new DataStore<>();
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

  private static RoutingMiddleware setupApp(Router router, DataStore<Integer, String> dataStore){
    UsersGetRequestMiddleware usersGetRequestMiddleware = new UsersGetRequestMiddleware(dataStore);
    UsersPutRequestMiddleware usersPutRequestMiddleware = new UsersPutRequestMiddleware(dataStore, usersGetRequestMiddleware);
    UsersDeleteRequestMiddleware usersDeleteRequestMiddleware = new UsersDeleteRequestMiddleware(dataStore, usersPutRequestMiddleware);
    ValidIdMiddleware app = new ValidIdMiddleware(dataStore, usersDeleteRequestMiddleware);
    return new RoutingMiddleware(router, app);
  }
}
