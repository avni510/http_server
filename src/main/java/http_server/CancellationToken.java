package http_server;

public interface CancellationToken {
  boolean isListening();
  void cancel();
}
