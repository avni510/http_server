package http_server;

public interface Processor {
  public void execute(Connection clientConnection) throws Exception;
}
