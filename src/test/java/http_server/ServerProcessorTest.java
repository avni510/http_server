package http_server;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;

public class ServerProcessorTest {

  private Socket createMockSocket(String request){
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(request.getBytes());
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    return new MockSocket(byteArrayInputStream, byteArrayOutputStream);
  }

  @Test
  public void responseIsWrittenOutForValidRequest() throws Exception {
    String request = "GET /hello_world HTTP/1.1\r\nHost: localhost\r\n\r\n";
    Socket socket = createMockSocket(request);
    MockServerSocketConnection serverSocketConnection = new MockServerSocketConnection(socket);
    serverSocketConnection.setStoredInputData(request);
    Router router = new Router();
    router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldHandler());
    ServerProcessor serverProcessor = new ServerProcessor(serverSocketConnection, router);

    serverProcessor.run();

    String response = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n" + "hello world";
    assertEquals(response, serverSocketConnection.getStoredOutputData());
  }

  @Test
  public void responseIsWrittenOutForInvalidRequest() throws Exception {
    String request = "/ HTTP/1.1\r\nHost: localhost\r\n\r\n";
    Socket socket = createMockSocket(request);
    MockServerSocketConnection serverSocketConnection = new MockServerSocketConnection(socket);
    serverSocketConnection.setStoredInputData(request);
    Router router = new Router();
    ServerProcessor serverProcessor = new ServerProcessor(serverSocketConnection, router);

    serverProcessor.run();

    String response = "HTTP/1.1 400 Bad Request\r\n\r\n";
    assertEquals(response, serverSocketConnection.getStoredOutputData());
  }
}