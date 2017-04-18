package http_server;

public interface CancellationToken {
  public boolean isListening();
  public void cancel();
}
