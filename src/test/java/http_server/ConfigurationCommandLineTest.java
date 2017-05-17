package http_server;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigurationCommandLineTest {
  private String rootPath = System.getProperty("user.dir");

  @Test
  public void directoryAndPortAreParsed(){
    Router router = new Router();
    ConfigurationCommandLine configurationCommandLine = new ConfigurationCommandLine(router);

    String[] commandLineArgs = {"-p", "5000", "-d", rootPath + "/new_directory"};
    configurationCommandLine.parse(commandLineArgs);

    assertEquals(rootPath + "/new_directory", configurationCommandLine.getDirectoryName());
    assertEquals(new Integer(5000), configurationCommandLine.getPortNumber());

  }

  @Test
  public void defaultDirectoryPathIsReturned() {
    Router router = new Router();
    ConfigurationCommandLine configurationCommandLine = new ConfigurationCommandLine(router);

    String[] commandLineArgs = {"-p", "5000"};
    configurationCommandLine.parse(commandLineArgs);

    assertEquals(rootPath + "/code", configurationCommandLine.getDirectoryName());
    assertEquals(new Integer(5000), configurationCommandLine.getPortNumber());
  }

  @Test
  public void defaultPortIsReturned() {
    Router router = new Router();
    ConfigurationCommandLine configurationCommandLine = new ConfigurationCommandLine(router);

    String[] commandLineArgs = {"-d", rootPath + "/new_directory"};
    configurationCommandLine.parse(commandLineArgs);

    assertEquals(rootPath + "/new_directory", configurationCommandLine.getDirectoryName());
    assertEquals(new Integer(4444), configurationCommandLine.getPortNumber());
  }

  @Test
  public void defaultDirectoryPathAndPortAreReturned() {
    Router router = new Router();
    ConfigurationCommandLine configurationCommandLine = new ConfigurationCommandLine(router);

    String[] commandLineArgs = {};
    configurationCommandLine.parse(commandLineArgs);

    assertEquals(rootPath + "/code", configurationCommandLine.getDirectoryName());
    assertEquals(new Integer(4444), configurationCommandLine.getPortNumber());
  }
}