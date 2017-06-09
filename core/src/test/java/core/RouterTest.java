package core;

import core.handler.BaseHandler;
import core.handler.ErrorHandler;

import core.request.RequestMethod;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class RouterTest {

  @Test
  public void routeIsRetrieved() throws Exception {
    Router router = new Router();
    BaseHandler baseHandler = new BaseHandler();
    ErrorHandler errorHandler = new ErrorHandler(HttpCodes.NOT_FOUND);
    router.addRoute(RequestMethod.GET, "/hello_world", baseHandler);
    router.addRoute(RequestMethod.GET, "/error", errorHandler);

    Handler handler = router.retrieveHandler(RequestMethod.GET, "/hello_world");

    assertEquals(handler, baseHandler);
  }

  @Test
  public void routeWithQueryParamsIsHandled() throws Exception {
    Router router = new Router();
    BaseHandler baseHandler = new BaseHandler();
    ErrorHandler errorHandler = new ErrorHandler(HttpCodes.NOT_FOUND);
    router.addRoute(RequestMethod.GET, "/parameters", baseHandler);
    router.addRoute(RequestMethod.GET, "/error", errorHandler);

    Handler handler = router.retrieveHandler(RequestMethod.GET, "/parameters?variable_1=foo");

    assertEquals(handler, baseHandler);
  }

  @Test
  public void dynamicRouteIsHandled() throws Exception {
    Router router = new Router();
    BaseHandler baseHandler = new BaseHandler();
    ErrorHandler errorHandler = new ErrorHandler(HttpCodes.NOT_FOUND);
    router.addRoute(RequestMethod.GET, "/foo/:id", baseHandler);
    router.addRoute(RequestMethod.GET, "/error", errorHandler);

    Handler handler = router.retrieveHandler(RequestMethod.GET, "/foo/1");

    assertEquals(handler, baseHandler);
  }

  @Test
  public void dynamicRouteForEditIsHandled() throws Exception {
    Router router = new Router();
    BaseHandler baseHandler = new BaseHandler();
    ErrorHandler errorHandler = new ErrorHandler(HttpCodes.NOT_FOUND);
    router.addRoute(RequestMethod.GET, "/foo/:id/edit", baseHandler);
    router.addRoute(RequestMethod.GET, "/error", errorHandler);

    Handler handler = router.retrieveHandler(RequestMethod.GET, "/foo/1/edit");

    assertEquals(handler, baseHandler);
  }

  @Test
  public void nullIsReturnedIfTheUriIsLongerThanSpecified() throws Exception {
    Router router = new Router();
    BaseHandler baseHandler = new BaseHandler();
    router.addRoute(RequestMethod.GET, "/foo/:id/edit", baseHandler);

    Handler handler = router.retrieveHandler(RequestMethod.GET, "/foo/1/edit/new");

    assertEquals(handler, null);
  }

  @Test
  public void nullIsReturnedIfTheUriIsShorterThanSpecified() throws Exception {
    Router router = new Router();
    BaseHandler baseHandler = new BaseHandler();
    router.addRoute(RequestMethod.GET, "/foo/:id/edit", baseHandler);

    Handler handler = router.retrieveHandler(RequestMethod.GET, "/foo");

    assertEquals(handler, null);
  }

  @Test
  public void nullIsReturnedIfTheUriIfASpecialCharacterIsInTheUri() throws Exception {
    Router router = new Router();
    BaseHandler baseHandler = new BaseHandler();
    router.addRoute(RequestMethod.GET, "/foo/:id/edit", baseHandler);

    Handler handler = router.retrieveHandler(RequestMethod.GET, "/foo/*/edit");

    assertEquals(handler, null);
  }

  @Test
  public void returnsTrueIfAUriIsInARouter() {
    Router router = new Router();
    router.addRoute(RequestMethod.GET, "/hello_world", new BaseHandler());

    boolean actualResult = router.uriExists("/hello_world");

    assertTrue(actualResult);
  }

  @Test
  public void returnsFalseIfAUriIsNotInARouter() {
    Router router = new Router();
    router.addRoute(RequestMethod.GET, "/hello_world", new BaseHandler());

    boolean actualResult = router.uriExists("/nonexistent_uri");

    assertFalse(actualResult);
  }
}
