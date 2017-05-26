package http_server;

public interface IExecutorService {
  void execute(Connection connection);
  void shutdown();
}
