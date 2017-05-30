package http_server;

public interface ServerExecutorService {
  void execute(Connection connection);
  void shutdown();
}
