package http_server;

public class HttpServer {
  private ConnectionManager server;
  private CancellationToken serverCancellationToken;
  private Processor serverProcessor;

  public HttpServer(ConnectionManager server, CancellationToken serverCancellationToken, Processor serverProcessor) {
    this.server = server;
    this.serverCancellationToken = serverCancellationToken;
    this.serverProcessor = serverProcessor;
  }

  public void run() throws Exception {
    while (serverCancellationToken.isListening()) {
      Connection serverSocketConnection = server.accept();
      serverProcessor.execute(serverSocketConnection);
    }
  }
}
