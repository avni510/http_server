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

  private String getParametersBody(){
    return "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"? variable_2 = stuff ";
  }

  @Test
  public void helloWorldIsSentAsAResponse() throws Exception {
    BufferedReader bufferedReader = getInputStream("/hello_world");
    Router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldHandler());

    Response actualResponse = Router.generateHttpResponse(bufferedReader);

    assertEquals("hello world", new String (actualResponse.getBody()));
  }

  @Test
  public void htmlOfFilesIsSentAsAResponse() throws Exception {
    String rootDirectoryPath = System.getProperty("user.dir") + "/code";
    BufferedReader bufferedReader = getInputStream("/");
    Router.addRoute(RequestMethod.GET, "/", new DirectoryHandler(rootDirectoryPath));

    Response actualResponse = Router.generateHttpResponse(bufferedReader);

    assertEquals(getHtmlBody(), new String(actualResponse.getBody()));
  }

  @Test
  public void fileContentsAreSentAsAResponseForResultFile() throws Exception {
    String filePath = System.getProperty("user.dir") + "/code/result.txt";
    BufferedReader bufferedReader = getInputStream("/result.txt");
    Router.addRoute(RequestMethod.GET, "/result.txt", new FileReaderHandler(filePath));

    Response actualResponse = Router.generateHttpResponse(bufferedReader);

    String fileContents = "module TimeLogger\nend\n";
    assertEquals(fileContents, new String(actualResponse.getBody()));
  }

  @Test
  public void fileContentsAreSentAsAResponseForValidationFile() throws Exception {
    String filePath = System.getProperty("user.dir") + "/code/validation.txt";
    BufferedReader bufferedReader = getInputStream("/validation.txt");
    Router.addRoute(RequestMethod.GET, "/validation.txt", new FileReaderHandler(filePath));

    Response actualResponse = Router.generateHttpResponse(bufferedReader);

    String fileContents = "x = 1\ny = 2\n";
    assertEquals(fileContents, new String(actualResponse.getBody()));
  }

  @Test
  public void notFoundResponseIsSentForNonExistentFile() throws Exception {
    String filePath = System.getProperty("user.dir") + "/code/validation.txt";
    BufferedReader bufferedReader = getInputStream("/main.txt");
    Router.addRoute(RequestMethod.GET, "/validation.txt", new FileReaderHandler(filePath));

    Response actualResponse = Router.generateHttpResponse(bufferedReader);

    assertEquals("404 Not Found", actualResponse.getStatusCodeMessage());
  }

  @Test
  public void badRequestResponseIsSentForMalformedRequest() throws Exception {
    String httpRequest = "/" + " HTTP/1.1\r\n\r\n";
    InputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

    Response actualResponse = Router.generateHttpResponse(bufferedReader);

    assertEquals("400 Bad Request", actualResponse.getStatusCodeMessage());
  }

  @Test
  public void methodNotAllowedError() throws Exception {
    String filePath = System.getProperty("user.dir") + "/code/result.txt";
    Router.addRoute(RequestMethod.GET, "/result.txt", new FileReaderHandler(filePath));
    String httpRequest = "POST " + "/result.txt" + " HTTP/1.1\r\nHost: localhost\r\n\r\n";
    InputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

    Response actualResponse = Router.generateHttpResponse(bufferedReader);

    assertEquals("405 Method Not Allowed", actualResponse.getStatusCodeMessage());
  }

  @Test
  public void uriWithQuestionMark() throws Exception {
    Router.addRoute(RequestMethod.GET, "/parameters", new ParameterHandler());
    String httpRequest = "GET " + "/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20" +
                         "!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%" +
                         "5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff" + " HTTP/1.1\r\nHost: localhost\r\n\r\n";
    InputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

    Response actualResponse = Router.generateHttpResponse(bufferedReader);

    assertEquals(getParametersBody(), new String (actualResponse.getBody()));
  }
}





