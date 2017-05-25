package http_server;

import static org.junit.Assert.assertEquals;

import http_server.handler.HelloWorldGetHandler;

import http_server.handler.directory.DirectoryGetHandler;
import http_server.middleware.FinalMiddleware;
import http_server.middleware.FileMiddleware;
import http_server.middleware.RoutingMiddleware;

import http_server.mocks.MockServerSocketConnection;
import http_server.mocks.MockSocket;

import http_server.request.RequestMethod;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.net.Socket;

public class ServerProcessorTest {
  private String rootDirectoryPath;
  private MockServerSocketConnection serverSocketConnection;

  private Socket createMockSocket(String request){
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(request.getBytes());
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    return new MockSocket(byteArrayInputStream, byteArrayOutputStream);
  }

  private void setupRouter(Router router){
    router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldGetHandler());
    router.addRoute(RequestMethod.GET, "/", new DirectoryGetHandler(rootDirectoryPath));
  }

  private RoutingMiddleware setupMiddlewares(Router router){
    FinalMiddleware finalMiddleware = new FinalMiddleware();
    String rootDirectoryPath = System.getProperty("user.dir") + "/code";
    FileMiddleware fileMiddleware = new FileMiddleware(rootDirectoryPath, finalMiddleware);
    return new RoutingMiddleware(router, fileMiddleware);
  }

  private String getHtmlBody() {
    return
        "<li> <a href=/image.png>" +
            "image.png</a></li>" +
            "<li> <a href=/result.txt>" +
            "result.txt</a></li>" +
            "<li> <a href=/validation.txt>" +
            "validation.txt</a></li>" +
            "<li> <a href=/log_time_entry.txt>" +
            "log_time_entry.txt</a></li>";
  }

  private ServerProcessor setup(String request){
    Socket socket = createMockSocket(request);
    rootDirectoryPath = System.getProperty("user.dir") + "/code";
    serverSocketConnection = new MockServerSocketConnection(socket);
    serverSocketConnection.setStoredInputData(request);
    Router router = new Router();
    setupRouter(router);
    Middleware app = setupMiddlewares(router);
    return new ServerProcessor(serverSocketConnection, app);
  }

  @Test
  public void responseIsWrittenOutForValidRequest() throws Exception {
    String request = "GET /hello_world HTTP/1.1" + Constants.CLRF + "Host: localhost" + Constants.CLRF + Constants.CLRF;
    ServerProcessor serverProcessor = setup(request);

    serverProcessor.run();

    String response = "HTTP/1.1 200 OK" + Constants.CLRF + "Content-Type: text/plain" +
                      Constants.CLRF + Constants.CLRF + "hello world";
    assertEquals(response, serverSocketConnection.getStoredOutputData());
  }

  @Test
  public void responseIsWrittenOutForInvalidRequest() throws Exception {
    String request = "/ HTTP/1.1" + Constants.CLRF + "Host: localhost" + Constants.CLRF + Constants.CLRF;
    ServerProcessor serverProcessor = setup(request);

    serverProcessor.run();

    String response = "HTTP/1.1 400 Bad Request" + Constants.CLRF + Constants.CLRF;
    assertEquals(response, serverSocketConnection.getStoredOutputData());
  }

  @Test
  public void htmlOfFilesIsSentAsAResponse() throws Exception {
    String request = "GET / HTTP/1.1" + Constants.CLRF + "Host: localhost" + Constants.CLRF + Constants.CLRF;
    ServerProcessor serverProcessor = setup(request);

    serverProcessor.run();

    String response = "HTTP/1.1 200 OK" + Constants.CLRF + "Content-Type: text/html" +
                      Constants.CLRF + Constants.CLRF + getHtmlBody();
    assertEquals(response, serverSocketConnection.getStoredOutputData());
  }

  @Test
  public void fileContentsAreSentAsAResponseForResultFile() throws Exception {
    String request = "GET /result.txt HTTP/1.1" + Constants.CLRF + "Host: localhost" + Constants.CLRF + Constants.CLRF;
    ServerProcessor serverProcessor = setup(request);

    serverProcessor.run();

    String fileContents = "module TimeLogger\nend\n";
    String response = "HTTP/1.1 200 OK" + Constants.CLRF + "ETag: cc640aa14e96c7e21003963620c42259125749d9" + Constants.CLRF +
                      "Content-Length: 22" + Constants.CLRF + "Content-Type: text/plain" +
                      Constants.CLRF + Constants.CLRF + fileContents;
    assertEquals(response, serverSocketConnection.getStoredOutputData());
  }

  @Test
  public void fileContentsAreSentAsAResponseForValidationFile() throws Exception {
    String request = "GET /validation.txt HTTP/1.1" + Constants.CLRF + "Host: localhost" +
                    Constants.CLRF + Constants.CLRF;
    ServerProcessor serverProcessor = setup(request);

    serverProcessor.run();

    String fileContents = "x = 1\ny = 2\n";
    String response = "HTTP/1.1 200 OK" + Constants.CLRF + "ETag: 488fbe72ea30312b860619e29290a226ee03dc56" +
                      Constants.CLRF + "Content-Length: 12" + Constants.CLRF + "Content-Type: text/plain" +
                      Constants.CLRF + Constants.CLRF + fileContents;
    assertEquals(response, serverSocketConnection.getStoredOutputData());
  }
}