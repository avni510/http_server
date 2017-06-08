package core;

public interface ServerExecutorService {
  void execute(Connection connection);

  void shutdown();
}
