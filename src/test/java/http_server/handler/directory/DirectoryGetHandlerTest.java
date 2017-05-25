package http_server.handler.directory;

import http_server.Constants;

import http_server.request.Request;
import http_server.request.RequestBuilder;
import http_server.request.RequestMethod;

import http_server.response.Response;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class DirectoryGetHandlerTest {

  public String getBody() {
   String body =
       "<li> <a href=/image.png>image.png</a></li>" +
       "<li> <a href=/result.txt>result.txt</a></li>" +
       "<li> <a href=/validation.txt>validation.txt</a></li>" +
       "<li> <a href=/log_time_entry.txt>log_time_entry.txt</a></li>";
   return body;
  }

  @Test
  public void responseIsReturnedForGetRequest() throws UnsupportedEncodingException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    String rootDirectory = System.getProperty("user.dir") + "/code";
    DirectoryGetHandler directoryGetHandler = new DirectoryGetHandler(rootDirectory);

    Response actualResponse = directoryGetHandler.generate(request);

    assertEquals("Content-Type: text/html" + Constants.CLRF, actualResponse.getHeaders());
    assertEquals(getBody(), new String (actualResponse.getBody()));
  }
}