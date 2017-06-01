package http_server.core;

public interface ServerExecutorService {
  void execute(Connection connection);
  void shutdown();
}
