package http_server;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

public class ConfigurationTest {
  private String rootPath = System.getProperty("user.dir");

  private String generateHelloWorldResponse() throws Exception {
    String request = "GET /hello_world HTTP/1.1\r\nHost: localhost\r\n\r\n";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    return Router.generateHttpResponse(bufferedReader);
  }


  @Test
  public void directoryAndPortAreParsed(){
    Configuration configuration = new Configuration();

    String[] commandLineArgs = {"-p", "5000", "-d", rootPath + "/new_directory"};
    configuration.parse(commandLineArgs);

    assertEquals(rootPath + "/new_directory", configuration.getDirectoryName());
    assertEquals(new Integer(5000), configuration.getPortNumber());

  }

  @Test
  public void defaultDirectoryPathIsReturned() {
    Configuration configuration = new Configuration();

    String[] commandLineArgs = {"-p", "5000"};
    configuration.parse(commandLineArgs);

    assertEquals(rootPath + "/code", configuration.getDirectoryName());
    assertEquals(new Integer(5000), configuration.getPortNumber());
  }

  @Test
  public void defaultPortIsReturned() {
    Configuration configuration = new Configuration();

    String[] commandLineArgs = {"-d", rootPath + "/new_directory"};
    configuration.parse(commandLineArgs);

    assertEquals(rootPath + "/new_directory", configuration.getDirectoryName());
    assertEquals(new Integer(4444), configuration.getPortNumber());
  }

  @Test
  public void defaultDirectoryPathAndPortAreReturned() {
    Configuration configuration = new Configuration();

    String[] commandLineArgs = {};
    configuration.parse(commandLineArgs);

    assertEquals(rootPath + "/code", configuration.getDirectoryName());
    assertEquals(new Integer(4444), configuration.getPortNumber());
  }

  @Test
  public void routesArePopulated() throws Exception {
    Configuration configuration = new Configuration();

    String[] commandLineArgs = {"-d", rootPath +"/code"};
    configuration.parse(commandLineArgs);
    configuration.populateRoutes();

    String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n" + "hello world";
    String actualResponse = generateHelloWorldResponse();
    assertEquals(expectedResponse, actualResponse);
  }
}