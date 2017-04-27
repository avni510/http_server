package http_server;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;

public class ServerProcessorTest {

  private String getHtmlBody() {
   return  "<li> <a href=/code/result.txt>" +
           "result.txt</a></li>" +
           "<li> <a href=/code/validation.txt>" +
           "validation.txt</a></li>" +
           "<li> <a href=/code/log_time_entry.txt>" +
           "log_time_entry.txt</a></li>";
  }

  private String setRequest(String path) {
    return "GET " + path + " HTTP/1.1\r\nHost: localhost\r\n\r\n";
  }

  private Socket createMockSocket(String request){
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(request.getBytes());
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    return new MockSocket(byteArrayInputStream, byteArrayOutputStream);
  }

  @Test
  public void sendsHelloWorld() throws Exception {
    String request = setRequest("/");
    Socket socket = createMockSocket(request);
    MockServerSocketConnection serverSocketConnection = new MockServerSocketConnection(socket);
    serverSocketConnection.setStoredInputData(request);
    ServerProcessor serverProcessor = new ServerProcessor();

    serverProcessor.execute(serverSocketConnection);

    String response = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n" + "hello world";
    assertEquals(response, serverSocketConnection.getStoredOutputData());
  }


  @Test
  public void sendsHtmlOfFilesInDirectory() throws Exception {
    String request = setRequest("/code");
    Socket socket = createMockSocket(request);
    MockServerSocketConnection serverSocketConnection = new MockServerSocketConnection(socket);
    serverSocketConnection.setStoredInputData(request);
    ServerProcessor serverProcessor = new ServerProcessor();

    serverProcessor.execute(serverSocketConnection);

    String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" + getHtmlBody();
    assertEquals(response, serverSocketConnection.getStoredOutputData());
  }
}