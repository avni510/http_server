package core;

import java.io.IOException;

public interface ConnectionManager {
  Connection accept() throws IOException;
  void close() throws IOException;
}
