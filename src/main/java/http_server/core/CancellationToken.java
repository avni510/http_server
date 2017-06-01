package http_server.core;

public interface CancellationToken {
  boolean isListening();
  void cancel();
  void setListeningCondition(boolean listening);
}
