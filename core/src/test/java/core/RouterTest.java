package core;

import core.handler.HelloWorldGetHandler;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class RouterTest {

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
