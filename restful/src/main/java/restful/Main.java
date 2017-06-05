package restful;

import core.Router;
import core.server.Server;
import core.server.ServerExecutor;
import core.server.ServerCancellationToken;
import core.server.HttpServer;
import core.DataStore;

import restful.configuration.ConfigurationRoutes;

import restful.middleware.UsersDeleteRequestMiddleware;
import restful.middleware.UsersPutRequestMiddleware;
import restful.middleware.UsersGetRequestMiddleware;
import restful.middleware.ValidIdMiddleware;

import core.middleware.RoutingMiddleware;

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
