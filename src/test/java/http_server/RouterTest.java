package http_server;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class RouterTest {

  private String getHtmlBody() {
    return  "<li> <a href=/code/result.txt>" +
        "result.txt</a></li>" +
        "<li> <a href=/code/validation.txt>" +
        "validation.txt</a></li>" +
        "<li> <a href=/code/log_time_entry.txt>" +
        "log_time_entry.txt</a></li>";
  }

  @Test
  public void helloWorldIsSentAsAResponse() throws Exception {
    String httpRequest = "GET " + "/" + " HTTP/1.1\r\nHost: localhost\r\n\r\n";
    InputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    Router router = new Router();
    router.populateRoutes(System.getProperty("user.dir") + "/code");

    String actualResponse = router.generateHttpResponse(bufferedReader);

    String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n" + "hello world";
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void htmlOfFilesIsSentAsAResponse() throws Exception {
    String httpRequest = "GET " + "/code" + " HTTP/1.1\r\nHost: localhost\r\n\r\n";
    InputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    Router router = new Router();
    router.populateRoutes(System.getProperty("user.dir") + "/code");

    String actualResponse = router.generateHttpResponse(bufferedReader);

    String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" + getHtmlBody();
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void fileContentsAreSentAsAResponseForResultFile() throws Exception {
    String httpRequest = "GET " + "/code/result.txt" + " HTTP/1.1\r\nHost: localhost\r\n\r\n";
    InputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    Router router = new Router();
    router.populateRoutes(System.getProperty("user.dir") + "/code");

    String actualResponse = router.generateHttpResponse(bufferedReader);

    String fileContents = "module TimeLogger\nend\n";
    String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n" + fileContents;
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void fileContentsAreSentAsAResponseForValidationFile() throws Exception {
    String httpRequest = "GET " + "/code/validation.txt" + " HTTP/1.1\r\nHost: localhost\r\n\r\n";
    InputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    Router router = new Router();
    router.populateRoutes(System.getProperty("user.dir") + "/code");

    String actualResponse = router.generateHttpResponse(bufferedReader);

    String fileContents = "x = 1\ny = 2\n";
    String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n" + fileContents;
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void notFoundResponseIsSentForNonExistentFile() throws Exception {
    String httpRequest = "GET " + "/code/main.txt" + " HTTP/1.1\r\nHost: localhost\r\n\r\n";
    InputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    Router router = new Router();
    router.populateRoutes(System.getProperty("user.dir") + "/code");

    String actualResponse = router.generateHttpResponse(bufferedReader);

    String expectedResponse = "HTTP/1.1 404 Not Found\r\n\r\n";
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void badRequestResponseIsSentForMalformedRequest() throws Exception {
    String httpRequest = "Invalid " + "/code/main.txt" + " HTTP/1.1\r\nHost: localhost\r\n\r\n";
    InputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    Router router = new Router();
    router.populateRoutes(System.getProperty("user.dir") + "/code");

    String actualResponse = router.generateHttpResponse(bufferedReader);

    String expectedResponse = "HTTP/1.1 400 Bad Request\r\n\r\n";
    assertEquals(expectedResponse, actualResponse);
  }
}