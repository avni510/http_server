package http_server;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class FormHandlerTest {

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

    FormHandler formHandler = new FormHandler(new DataStore());

    Response actualResponse = formHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("Content-Type: text/html\r\n", actualResponse.getHeaders());
    assertEquals(getBody(""), new String(actualResponse.getBody()));
  }

  @Test
  public void aPostRequestIsHandled() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.POST)
        .setUri("/form")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .setBody("data=fatcat")
        .build();
    FormHandler formHandler = new FormHandler(new DataStore());

    Response actualResponse = formHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
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

    FormHandler formHandler = new FormHandler(dataStore);

    Response actualResponse = formHandler.generate(request);

    assertEquals(getBody("data=fatcat"), new String(actualResponse.getBody()));
  }

  @Test
  public void aPutRequestIsHandled() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.PUT)
        .setUri("/form")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .setBody("data=heathcliff")
        .build();
    DataStore dataStore = new DataStore();
    dataStore.storeEntry("data", "fatcat");
    dataStore.storeEntry("data", "heathcliff");

    FormHandler formHandler = new FormHandler(dataStore);

    Response actualResponse = formHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }

  @Test
  public void aDeleteRequestIsHandled() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.DELETE)
        .setUri("/form")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost")
        .build();
    DataStore dataStore = new DataStore();
    dataStore.storeEntry("data", "fatcat");

    FormHandler formHandler = new FormHandler(dataStore);

    Response actualResponse = formHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}