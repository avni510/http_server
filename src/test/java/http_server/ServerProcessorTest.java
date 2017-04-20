package http_server;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ServerProcessorTest {

  public String getHtmlBody() {
   return  "<li> <a href=/code/result.txt>" +
           "result.txt</a></li>" +
           "<li> <a href=/code/validation.txt>" +
           "validation.txt</a></li>" +
           "<li> <a href=/code/log_time_entry.txt>" +
           "log_time_entry.txt</a></li>";
  }

  @Test
  public void sendsHelloWorld() throws Exception {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("GET / HTTP/1.1\r\nHost: localhost\r\n\r\n".getBytes());
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    MockSocket mockSocket = new MockSocket(byteArrayInputStream, byteArrayOutputStream);
    MockServerSocketConnection mockServerSocketConnection = new MockServerSocketConnection(mockSocket);
    mockServerSocketConnection.setStoredInputData("GET / HTTP/1.1\r\nHost: localhost\r\n\r\n");
    ServerProcessor serverProcessor = new ServerProcessor();
    serverProcessor.execute(mockServerSocketConnection);
    assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n" + "hello world", mockServerSocketConnection.getStoredOutputData());
  }


  @Test
  public void sendsHtmlOfFilesInDirectory() throws Exception {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("GET /code HTTP/1.1\r\nHost: localhost\r\n\r\n".getBytes());
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    MockSocket mockSocket = new MockSocket(byteArrayInputStream, byteArrayOutputStream);
    MockServerSocketConnection mockServerSocketConnection = new MockServerSocketConnection(mockSocket);
    mockServerSocketConnection.setStoredInputData("GET /code HTTP/1.1\r\nHost: localhost\r\n\r\n");
    ServerProcessor serverProcessor = new ServerProcessor();
    serverProcessor.execute(mockServerSocketConnection);
    assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" + getHtmlBody(), mockServerSocketConnection.getStoredOutputData());
  }
}