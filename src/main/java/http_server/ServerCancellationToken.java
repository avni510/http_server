package http_server;

public class ServerCancellationToken implements CancellationToken{
  private boolean listening;

  public ServerCancellationToken(boolean listening){
    this.listening = listening;
  }

  public boolean isListening() {
    return listening;
  }

  public void cancel(){
    listening = false;
  }
}
