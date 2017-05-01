package http_server;

import java.net.ServerSocket;

public class HTTPServer {

  public static void main(String[] args) throws Exception {
    ConfigurationValidation configurationValidation = new ConfigurationValidation();
    configurationValidation.exitForInvalidArgs(args);
    Configuration configuration = new Configuration();
    String directoryPath = System.getProperty("user.dir") + configuration.getDirectory(args);
    Integer port = configuration.getPort(args);
    ServerSocket serverSocket = new ServerSocket(port);
    ConnectionManager server = new Server (serverSocket);
    ServerCancellationToken serverCancellationToken = new ServerCancellationToken();
    Processor serverProcessor = new ServerProcessor();
    ServerListener serverListener = new ServerListener(server, serverCancellationToken, serverProcessor);
    serverListener.run(directoryPath);
  }
}
