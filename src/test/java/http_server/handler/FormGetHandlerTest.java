package http_server.handler;

import http_server.Request;
import http_server.Response;
import http_server.RequestBuilder;
import http_server.RequestMethod;
import http_server.DataStore;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FormGetHandlerTest {

  private String getBody(String storedData){
    return
        "<form action=\"/form\" method=\"post\">" +
          "Data: <br> " +
          "<input type=\"text\" name=\"data\">" +
          "<input type=\"submit\" value=\"Submit\">" +
        "</form> <br>" + "<p>" + storedData + "</p>";
  }

  @Test
  public void aFormIsSentForAGetRequestWithNoData() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/form")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();

    FormGetHandler formGetHandler = new FormGetHandler(new DataStore());

    Response actualResponse = formGetHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("Content-Type: text/html\r\n", actualResponse.getHeaders());
    assertEquals(getBody(""), new String(actualResponse.getBody()));
  }

  @Test
  public void aFormIsSentForAGetRequestWithData() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/form")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    DataStore dataStore = new DataStore();
    dataStore.storeEntry("data", "fatcat");

    FormGetHandler formGetHandler = new FormGetHandler(dataStore);

    Response actualResponse = formGetHandler.generate(request);

    assertEquals(getBody("data=fatcat"), new String(actualResponse.getBody()));
  }
}