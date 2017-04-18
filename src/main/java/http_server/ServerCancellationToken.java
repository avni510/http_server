package http_server;

public class ServerCancellationToken implements CancellationToken{
  private boolean listening = true;

  public boolean isListening() {
    return listening;
  }

  public void cancel(){
    listening = false;
  }
}
