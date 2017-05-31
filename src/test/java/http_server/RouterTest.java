package http_server;

import http_server.cobspec.handler.HelloWorldGetHandler;
import http_server.cobspec.handler.ParametersGetHandler;

import http_server.request.Request;
import http_server.request.RequestBuilder;
import http_server.request.RequestMethod;

import http_server.response.Response;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class RouterTest {

  private String getParametersBody(){
    return "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"? variable_2 = stuff ";
  }

  @Test
  public void routeIsAdded() throws Exception{
    Router router = new Router();

    router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldGetHandler());

    Map<Tuple<Enum<RequestMethod>, String>, Handler> allRoutes = router.getRoutes();
    Handler handler = allRoutes.get(new Tuple<>(RequestMethod.GET, "/hello_world"));
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/hello_world")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    Response actualResponse = handler.generate(request);
    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("hello world", new String (actualResponse.getBody()));
  }

  @Test
  public void routeIsRetrieved() throws Exception{
    Router router = new Router();
    router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldGetHandler());

    Handler handler = router.retrieveHandler(RequestMethod.GET, "/hello_world");

    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/hello_world")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    Response actualResponse = handler.generate(request);
    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("hello world", new String (actualResponse.getBody()));
  }

  @Test
  public void handlesARouteWithAQuestionMark() throws Exception{
    Router router = new Router();
    router.addRoute(RequestMethod.GET, "/parameters", new ParametersGetHandler());

    String httpUri = "/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20" +
                     "!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%" +
                     "5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff";
    Handler handler = router.retrieveHandler(RequestMethod.GET, httpUri);

    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri(httpUri)
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    Response actualResponse = handler.generate(request);
    assertEquals(getParametersBody(), new String(actualResponse.getBody()));

  }

  @Test
  public void returnsTrueIfAUriIsInARouter(){
    Router router = new Router();
    router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldGetHandler());

    boolean actualResult = router.uriExists("/hello_world");

    assertTrue(actualResult);
  }

  @Test
  public void returnsFalseIfAUriIsNotInARouter(){
    Router router = new Router();
    router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldGetHandler());

    boolean actualResult = router.uriExists("/nonexistent_uri");

    assertFalse(actualResult);
  }
}
