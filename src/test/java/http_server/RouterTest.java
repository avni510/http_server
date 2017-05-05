package http_server;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class RouterTest {

  private String getHtmlBody() {
    return  "<li> <a href=/result.txt>" +
        "result.txt</a></li>" +
        "<li> <a href=/validation.txt>" +
        "validation.txt</a></li>" +
        "<li> <a href=/log_time_entry.txt>" +
        "log_time_entry.txt</a></li>";
  }

  private BufferedReader getInputStream(String uri){
    String httpRequest = "GET " + uri + " HTTP/1.1\r\nHost: localhost\r\n\r\n";
    InputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes());
    return new BufferedReader(new InputStreamReader(inputStream));
  }

  @Test
  public void helloWorldIsSentAsAResponse() throws Exception {
    BufferedReader bufferedReader = getInputStream("/hello_world");
    Router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldHandler());

    String actualResponse = Router.generateHttpResponse(bufferedReader);

    String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n" + "hello world";
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void htmlOfFilesIsSentAsAResponse() throws Exception {
    String rootDirectoryPath = System.getProperty("user.dir") + "/code";
    BufferedReader bufferedReader = getInputStream("/");
    Router.addRoute(RequestMethod.GET, "/", new DirectoryHandler(rootDirectoryPath));

    String actualResponse = Router.generateHttpResponse(bufferedReader);

    String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" + getHtmlBody();
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void fileContentsAreSentAsAResponseForResultFile() throws Exception {
    String filePath = System.getProperty("user.dir") + "/code/result.txt";
    BufferedReader bufferedReader = getInputStream("/result.txt");
    Router.addRoute(RequestMethod.GET, "/result.txt", new FileReaderHandler(filePath));

    String actualResponse = Router.generateHttpResponse(bufferedReader);

    String fileContents = "module TimeLogger\nend\n";
    String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n" + fileContents;
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void fileContentsAreSentAsAResponseForValidationFile() throws Exception {
    String filePath = System.getProperty("user.dir") + "/code/validation.txt";
    BufferedReader bufferedReader = getInputStream("/validation.txt");
    Router.addRoute(RequestMethod.GET, "/validation.txt", new FileReaderHandler(filePath));

    String actualResponse = Router.generateHttpResponse(bufferedReader);

    String fileContents = "x = 1\ny = 2\n";
    String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n" + fileContents;
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void notFoundResponseIsSentForNonExistentFile() throws Exception {
    String filePath = System.getProperty("user.dir") + "/code/validation.txt";
    BufferedReader bufferedReader = getInputStream("/main.txt");
    Router.addRoute(RequestMethod.GET, "/validation.txt", new FileReaderHandler(filePath));

    String actualResponse = Router.generateHttpResponse(bufferedReader);

    String expectedResponse = "HTTP/1.1 404 Not Found\r\n\r\n";
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void badRequestResponseIsSentForMalformedRequest() throws Exception {
    String httpRequest = "INVALID " + "/" + " HTTP/1.1\r\nHost: localhost\r\n\r\n";
    InputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

    String actualResponse = Router.generateHttpResponse(bufferedReader);

    String expectedResponse = "HTTP/1.1 400 Bad Request\r\n\r\n";
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void methodNotAllowedError() throws Exception {
    String filePath = System.getProperty("user.dir") + "/code/result.txt";
    Router.addRoute(RequestMethod.GET, "/result.txt", new FileReaderHandler(filePath));
    String httpRequest = "POST " + "/result.txt" + " HTTP/1.1\r\nHost: localhost\r\n\r\n";
    InputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

    String actualResponse = Router.generateHttpResponse(bufferedReader);

    String expectedResponse = "HTTP/1.1 405 Method Not Allowed\r\n\r\n";
    assertEquals(expectedResponse, actualResponse);
  }
}





