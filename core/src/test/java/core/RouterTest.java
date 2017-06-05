package core;

import core.handler.BaseHandler;

import core.request.RequestMethod;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RouterTest {

  @Test
  public void routeIsAdded() throws Exception{
    Router router = new Router();

    router.addRoute(RequestMethod.GET, "/hello_world", new BaseHandler());

    Handler handler = router.retrieveHandler(RequestMethod.GET, "/hello_world");
    assertThat(handler, instanceOf(BaseHandler.class));
  }

  @Test
  public void routeIsRetrieved() throws Exception{
    Router router = new Router();
    router.addRoute(RequestMethod.GET, "/hello_world", new BaseHandler());

    Handler handler = router.retrieveHandler(RequestMethod.GET, "/hello_world");

    assertThat(handler, instanceOf(BaseHandler.class));
  }

  @Test
  public void returnsTrueIfAUriIsInARouter(){
    Router router = new Router();
    router.addRoute(RequestMethod.GET, "/hello_world", new BaseHandler());

    boolean actualResult = router.uriExists("/hello_world");

    assertTrue(actualResult);
  }

  @Test
  public void returnsFalseIfAUriIsNotInARouter(){
    Router router = new Router();
    router.addRoute(RequestMethod.GET, "/hello_world", new BaseHandler());

    boolean actualResult = router.uriExists("/nonexistent_uri");

    assertFalse(actualResult);
  }
}
