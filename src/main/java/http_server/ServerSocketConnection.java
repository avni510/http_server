package http_server;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.Socket;

public class ServerSocketConnection implements Connection {
  private Socket clientSocket;

  public ServerSocketConnection(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  public InputStream in() throws IOException {
    return clientSocket.getInputStream();
  }

  public OutputStream out() throws IOException {
    return clientSocket.getOutputStream();
  }
}
