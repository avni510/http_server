package core.request;

import core.Constants;

import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RequestTest {

  @Test
  public void requestMethodIsReturned() {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHeader("Host: localhost")
        .setBody("data=fatcat")
        .build();

    assertEquals(RequestMethod.GET, request.getRequestMethod());
  }

  @Test
  public void uriIsReturned() {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHeader("Host: localhost")
        .setBody("data=fatcat")
        .build();

    assertEquals("/", request.getUri());
  }

  @Test
  public void httpVersionIsReturned() {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHeader("Host: localhost")
        .setBody("data=fatcat")
        .build();

    assertEquals("HTTP/1.1", request.getHttpVersion());
  }

  @Test
  public void headerIsReturned() {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHeader("Host: localhost" + Constants.CLRF + "Content-Type: text/plain")
        .setBody("hello world")
        .build();
    Header header = new Header();

    Map<String, String> actualResult = request.getHeader();

    header.add("Host", "localhost");
    header.add("Content-Type", "text/plain");
    Map<String, String> expectedResult = header.getAllHeaders();
    assertTrue(expectedResult.equals(actualResult));
  }

  @Test
  public void bodyIsReturned() {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHeader("Host: localhost")
        .setBody("data=fatcat")
        .build();

    String actualResult = request.getEntireBody();

    assertTrue("data=fatcat".equals(actualResult));
  }

  @Test
  public void bodyParameterIsReturn() {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHeader("Host: localhost")
        .setBody("data=fatcat")
        .build();

    String actualResult = request.getBodyParam("data");

    assertEquals("fatcat", actualResult);
  }

  @Test
  public void requestIsReturnedWithNoBody() {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHeader("Host: localhost")
        .build();

    String actualResult = request.getEntireBody();

    assertEquals(null, actualResult);
  }

  @Test
  public void noParametersAreReturnedIfNoBodyExists() {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHeader("Host: localhost")
        .build();

    String actualResult = request.getBodyParam("data");

    assertEquals(null, actualResult);
  }

  @Test
  public void idInAUriIsReturned() {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users/1")
        .setHeader("Host: localhost")
        .build();

    Integer actualResult = request.getIdInUri();

    assertEquals((Integer) 1, actualResult);
  }

  @Test
  public void idInAUriIsReturnedForEditRequest() {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users/1/edit")
        .setHeader("Host: localhost")
        .build();

    Integer actualResult = request.getIdInUri();

    assertEquals((Integer) 1, actualResult);
  }

  @Test
  public void nullIsReturnedForAUriWithNoId() {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users")
        .setHeader("Host: localhost")
        .build();

    Integer actualResult = request.getIdInUri();

    assertEquals(null, actualResult);
  }

  @Test
  public void allBodyParamsAreReturned() {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/users")
        .setHeader("Host: localhost")
        .setBody("_method=put&username=foo")
        .build();

    String actualMethodValue = request.getBodyParam("_method");
    String actualUsernameValue = request.getBodyParam("username");

    assertEquals("put", actualMethodValue);
    assertEquals("foo", actualUsernameValue);
  }
}