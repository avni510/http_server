package cobspec.handler.form;

import core.Constants;

import core.DataStore;
import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

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

    FormGetHandler formGetHandler = new FormGetHandler(new DataStore<String, String>());

    Response actualResponse = formGetHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("Content-Type: text/html" + Constants.CLRF, actualResponse.getHeaders());
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
    DataStore<String, String> dataStore = new DataStore<String, String>();
    dataStore.storeEntry("data", "fatcat");

    FormGetHandler formGetHandler = new FormGetHandler(dataStore);

    Response actualResponse = formGetHandler.generate(request);

    assertEquals(getBody("data=fatcat"), new String(actualResponse.getBody()));
  }
}