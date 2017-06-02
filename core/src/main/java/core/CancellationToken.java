package core;

public interface CancellationToken {
  boolean isListening();
  void cancel();
  void setListeningCondition(boolean listening);
}
