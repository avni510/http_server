package http_server;

import java.io.InputStream;
import java.io.OutputStream;

import java.net.Socket;

class MockSocket extends Socket {
  private InputStream inputStream;
  private OutputStream outputStream;

  public MockSocket (InputStream inputstream, OutputStream outputStream) {
    this.inputStream = inputstream;
    this.outputStream = outputStream;
  }

  @Override
  public InputStream getInputStream(){
    return inputStream;
  }

  @Override
  public OutputStream getOutputStream(){
    return outputStream;
  }
}

