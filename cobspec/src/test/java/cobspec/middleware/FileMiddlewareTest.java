package cobspec.middleware;

import core.Handler;
import core.Middleware;

import core.middleware.FinalMiddleware;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileMiddlewareTest {

  @Test
  public void responseForFileIsReturned() throws Exception {
    String rootDirectoryPath = System.getProperty("user.dir") + "/code";
    Middleware nextMiddleware = new FinalMiddleware();
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/result.txt")
        .setHeader("Host: localhost")
        .build();
    FileMiddleware fileMiddleware = new FileMiddleware(rootDirectoryPath, nextMiddleware);

    Handler handler = fileMiddleware.call(request);

    Response actualResponse = handler.generate(request);
    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("module TimeLogger\nend\n", new String(actualResponse.getBody()));
  }

  @Test
  public void responseForFileIsCreatedAsNeeded() throws Exception {
    String rootDirectoryPath = System.getProperty("user.dir") + "/code";
    Middleware nextMiddleware = new FinalMiddleware();
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/validation.txt")
        .setHeader("Host: localhost")
        .build();
    FileMiddleware fileMiddleware = new FileMiddleware(rootDirectoryPath, nextMiddleware);

    Handler handler = fileMiddleware.call(request);

    Response actualResponse = handler.generate(request);
    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("x = 1\ny = 2\n", new String(actualResponse.getBody()));
  }

  @Test
  public void methodNotAllowedErrorIsReturned() throws Exception {
    String rootDirectoryPath = System.getProperty("user.dir") + "/code";
    Middleware nextMiddleware = new FinalMiddleware();
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.PUT)
        .setUri("/validation.txt")
        .setHeader("Host: localhost")
        .build();
    FileMiddleware fileMiddleware = new FileMiddleware(rootDirectoryPath, nextMiddleware);

    Handler handler = fileMiddleware.call(request);

    Response actualResponse = handler.generate(request);
    assertEquals("405 Method Not Allowed", actualResponse.getStatusCodeMessage());
  }

  @Test
  public void nextMiddlewareIsCalled() throws Exception {
    String rootDirectoryPath = System.getProperty("user.dir") + "/code";
    Middleware nextMiddleware = new FinalMiddleware();
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/nonexistent_route")
        .setHeader("Host: localhost")
        .build();
    FileMiddleware fileMiddleware = new FileMiddleware(rootDirectoryPath, nextMiddleware);

    Handler handler = fileMiddleware.call(request);

    Response actualResponse = handler.generate(request);
    assertEquals("404 Not Found", actualResponse.getStatusCodeMessage());
  }
}