package http_server;

import java.io.IOException;

public interface ConnectionManager {
  Connection accept() throws IOException;
  void close() throws IOException;
}
