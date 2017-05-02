package http_server;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

public class ConfigurationTest {

  private String generateHelloWorldResponse() throws Exception {
    String request = "GET / HTTP/1.1\r\nHost: localhost\r\n\r\n";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    return Router.generateHttpResponse(bufferedReader);
  }


  @Test
  public void directoryAndPortAreParsed(){
    Configuration configuration = new Configuration();

    String[] commandLineArgs = {"-p", "5000", "-d", "/new_directory"};
    configuration.parse(commandLineArgs);

    assertEquals("/new_directory", configuration.getDirectoryName());
    assertEquals(new Integer(5000), configuration.getPortNumber());

  }

  @Test
  public void defaultDirectoryPathIsReturned() {
    Configuration configuration = new Configuration();

    String[] commandLineArgs = {"-p", "5000"};
    configuration.parse(commandLineArgs);

    assertEquals("/code", configuration.getDirectoryName());
    assertEquals(new Integer(5000), configuration.getPortNumber());
  }

  @Test
  public void defaultPortIsReturned() {
    Configuration configuration = new Configuration();

    String[] commandLineArgs = {"-d", "/new_directory"};
    configuration.parse(commandLineArgs);

    assertEquals("/new_directory", configuration.getDirectoryName());
    assertEquals(new Integer(4444), configuration.getPortNumber());
  }

  @Test
  public void defaultDirectoryPathAndPortAreReturned() {
    Configuration configuration = new Configuration();

    String[] commandLineArgs = {};
    configuration.parse(commandLineArgs);

    assertEquals("/code", configuration.getDirectoryName());
    assertEquals(new Integer(4444), configuration.getPortNumber());
  }

  @Test
  public void slashBeforeDirectoryNameIsCreated(){
    Configuration configuration = new Configuration();

    String[] commandLineArgs = {"-d", "new_directory"};
    configuration.parse(commandLineArgs);

    assertEquals("/new_directory", configuration.getDirectoryName());
  }

  @Test
  public void routesArePopulated() throws Exception {
    Configuration configuration = new Configuration();

    String[] commandLineArgs = {"-d", "/code"};
    configuration.parse(commandLineArgs);
    configuration.populateRoutes();

    String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n" + "hello world";
    String actualResponse = generateHelloWorldResponse();
    assertEquals(expectedResponse, actualResponse);
  }
}