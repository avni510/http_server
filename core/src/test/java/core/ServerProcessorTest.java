package core;

import static org.junit.Assert.assertEquals;

import core.handler.BaseHandler;

import core.middleware.FinalMiddleware;
import core.middleware.RoutingMiddleware;

import core.mocks.MockServerSocketConnection;
import core.mocks.MockSocket;

import core.request.RequestMethod;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.net.Socket;

public class ServerProcessorTest {
  private MockServerSocketConnection serverSocketConnection;

  private Socket createMockSocket(String request){
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(request.getBytes());
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    return new MockSocket(byteArrayInputStream, byteArrayOutputStream);
  }

  private void setupRouter(Router router){
    router.addRoute(RequestMethod.GET, "/hello_world", new BaseHandler());
  }

  private RoutingMiddleware setupMiddlewares(Router router){
    FinalMiddleware finalMiddleware = new FinalMiddleware();
    return new RoutingMiddleware(router, finalMiddleware);
  }

  private ServerProcessor setup(String request){
    Socket socket = createMockSocket(request);
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
}