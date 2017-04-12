package http_server;

import com.sun.xml.internal.ws.org.objectweb.asm.ByteVector;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerTest {

  @Test
  public void theServerStopsListening() throws Exception {
    ServerSocket mockServerSocket = new MockServerSocket(4442);
    CancellationToken mockServerCancellationToken = new MockServerCancellationToken();
    ServerListener serverListener = new ServerListener(mockServerSocket, mockServerCancellationToken);
    serverListener.runner();
    assertEquals(false, mockServerCancellationToken.isListening());
  }

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

  class MockServerSocket extends ServerSocket {

    public MockServerSocket(int port) throws IOException {
      super(port);
    }

    @Override
    public Socket accept() throws IOException {
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("GET / HTTP/1.1\r\nHost: localhost\r\n\r\n".getBytes());
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      return new MockSocket(byteArrayInputStream, byteArrayOutputStream);
    }
  }
}