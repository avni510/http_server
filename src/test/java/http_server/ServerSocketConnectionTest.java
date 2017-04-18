package http_server;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.Socket;

public class ServerSocketConnectionTest {
  private InputStream inputStream;
  private OutputStream outputStream;
  private ServerSocketConnection serverSocketConnection;

  @Before
  public void setUp() {
    inputStream = new ByteArrayInputStream("".getBytes());
    outputStream = new ByteArrayOutputStream();
    Socket clientSocket = new MockSocket(inputStream, outputStream);
    serverSocketConnection = new ServerSocketConnection(clientSocket);
  }

  @Test
  public void InputStreamIsReturned() throws Exception {
    assertEquals(inputStream, serverSocketConnection.in());
  }

  @Test
  public void OutputStreamIsReturned() throws Exception {
    assertEquals(outputStream, serverSocketConnection.out());
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
}