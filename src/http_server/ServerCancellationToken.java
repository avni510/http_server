package http_server;

public class ServerCancellationToken implements CancellationToken{

  public boolean isListening() {
    return true;
  }
}
