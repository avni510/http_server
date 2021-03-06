package core.response;

import core.Constants;
import core.HttpCodes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class ResponseTest {

  @Test
  public void httpResponseIstReturnedWithHeaderAndBody() {
    Response response = new ResponseBuilder()
        .setStatusCode(HttpCodes.OK)
        .setHeader("Content-Type", "text/plain")
        .setBody("hello world")
        .build();

    byte[] expectedHttpResponse = ("HTTP/1.1 200 OK" + Constants.CLRF + "Content-Type: text/plain" +
        Constants.CLRF + Constants.CLRF + "hello world").getBytes();
    assertTrue(Arrays.equals(expectedHttpResponse, response.getHttpResponseBytes()));
  }

  @Test
  public void httpResponseIstReturnedWithNoHeaderAndBody() {
    Response response = new ResponseBuilder()
        .setStatusCode(HttpCodes.NOT_FOUND)
        .build();

    byte[] expectedHttpResponse = ("HTTP/1.1 404 Not Found" + Constants.CLRF + Constants.CLRF).getBytes();

    assertTrue(Arrays.equals(expectedHttpResponse, response.getHttpResponseBytes()));
  }

  @Test
  public void httpResponseIstReturnedWithHeaderButNoBody() {
    Response response = new ResponseBuilder()
        .setStatusCode(HttpCodes.FOUND)
        .setHeader("Location", "http://localhost:4444/")
        .build();

    byte[] expectedHttpResponse = ("HTTP/1.1 302 Found" + Constants.CLRF + "Location: http://localhost:4444/" +
        Constants.CLRF + Constants.CLRF).getBytes();

    assertTrue(Arrays.equals(expectedHttpResponse, response.getHttpResponseBytes()));
  }
}