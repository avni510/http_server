package http_server;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .build();

    FormHandler formHandler = new FormHandler(new DataStore());

    String actualResponse = formHandler.generate(request);

    String expectedResponse = "HTTP/1.1 200 OK\r\n" +
                              "Content-Type: text/html\r\n\r\n" +
                              getBody("");
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void aPostRequestIsHandled() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.POST)
        .setUri("/form")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .setBody("data=fatcat")
        .build();
    FormHandler formHandler = new FormHandler(new DataStore());

    String actualResponse = formHandler.generate(request);

    String expectedResponse = "HTTP/1.1 200 OK\r\n\r\n";
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void aFormIsSentForAGetRequestWithData() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/form")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .build();
    DataStore dataStore = new DataStore();
    dataStore.storeEntry("data", "fatcat");

    FormHandler formHandler = new FormHandler(dataStore);

    String actualResponse = formHandler.generate(request);

    String expectedResponse = "HTTP/1.1 200 OK\r\n" +
                              "Content-Type: text/html\r\n\r\n" +
                              getBody("data=fatcat");
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void aPutRequestIsHandled() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.PUT)
        .setUri("/form")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .setBody("data=heathcliff")
        .build();
    DataStore dataStore = new DataStore();
    dataStore.storeEntry("data", "fatcat");
    dataStore.storeEntry("data", "heathcliff");

    FormHandler formHandler = new FormHandler(dataStore);

    String actualResponse = formHandler.generate(request);

    String expectedResponse = "HTTP/1.1 200 OK\r\n\r\n";
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void aDeleteRequestIsHandled() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.DELETE)
        .setUri("/form")
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .build();
    DataStore dataStore = new DataStore();
    dataStore.storeEntry("data", "fatcat");

    FormHandler formHandler = new FormHandler(dataStore);

    String actualResponse = formHandler.generate(request);

    String expectedResponse = "HTTP/1.1 200 OK\r\n\r\n";
    assertEquals(expectedResponse, actualResponse);
  }
}