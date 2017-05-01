package http_server;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigurationTest {

  @Test
  public void relativeDirectoryPathIsReturned() {
   Configuration configuration = new Configuration();

   String[] commandLineArgs = {"-p", "5000", "-d", "/new_directory"};
   String actualResult = configuration.getDirectory(commandLineArgs);

   assertEquals("/new_directory", actualResult);
  }

  @Test
  public void portNumberIsReturned() {
    Configuration configuration = new Configuration();

    String[] commandLineArgs = {"-p", "5000", "-d", "/new_directory"};
    Integer actualResult = configuration.getPort(commandLineArgs);

    assertEquals(new Integer(5000), actualResult);
  }

  @Test
  public void defaultDirectoryPathIsReturned() {
    Configuration configuration = new Configuration();

    String[] commandLineArgs = {"-p", "5000"};
    String actualResult = configuration.getDirectory(commandLineArgs);

    assertEquals("/code", actualResult);
  }

  @Test
  public void defaultPortIsReturned() {
    Configuration configuration = new Configuration();

    String[] commandLineArgs = {"-d", "/new_directory"};
    Integer actualResult = configuration.getPort(commandLineArgs);

    assertEquals(new Integer(4444), actualResult);
  }

  @Test
  public void defaultDirectoryPathAndPortAreReturned() {
    Configuration configuration = new Configuration();

    String[] commandLineArgs = {};
    String actualDirectory = configuration.getDirectory(commandLineArgs);
    Integer actualPort = configuration.getPort(commandLineArgs);

    assertEquals(new Integer(4444), actualPort);
    assertEquals("/code", actualDirectory);
  }

  @Test
  public void argumentIsFound() {
    Configuration configuration = new Configuration();

    String[] commandLineArgs = {"-d", "/new_directory"};
    String actualResult = configuration.findArg(commandLineArgs, "-d", "/code");

    assertEquals("/new_directory", actualResult);
  }

}