package http_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public interface ConnectionManager {
  Connection accept() throws IOException;
}
