package http_server;

import java.io.IOException;
import java.net.Socket;

public interface ConnectionManager {
  public Socket accept() throws IOException;
}
