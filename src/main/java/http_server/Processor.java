package http_server;

public interface Processor extends Runnable {
  void setClientConnection(Connection clientConnection);
}
