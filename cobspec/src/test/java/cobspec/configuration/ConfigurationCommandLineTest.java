package cobspec.configuration;

import cobspec.configuration.ConfigurationCommandLine;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigurationCommandLineTest {
  private String rootPath = System.getProperty("user.dir");

  @Test
  public void directoryAndPortAreParsed(){
    ConfigurationCommandLine configurationCommandLine = new ConfigurationCommandLine();

    String[] commandLineArgs = {"-p", "5000", "-d", rootPath + "/new_directory"};
    configurationCommandLine.parse(commandLineArgs);

    assertEquals(rootPath + "/new_directory", configurationCommandLine.getDirectoryName());
    assertEquals(new Integer(5000), configurationCommandLine.getPortNumber());

  }

  @Test
  public void defaultDirectoryPathIsReturned() {
    ConfigurationCommandLine configurationCommandLine = new ConfigurationCommandLine();

    String[] commandLineArgs = {"-p", "5000"};
    configurationCommandLine.parse(commandLineArgs);

    assertEquals(rootPath + "/code", configurationCommandLine.getDirectoryName());
    assertEquals(new Integer(5000), configurationCommandLine.getPortNumber());
  }

  @Test
  public void defaultPortIsReturned() {
    ConfigurationCommandLine configurationCommandLine = new ConfigurationCommandLine();

    String[] commandLineArgs = {"-d", rootPath + "/new_directory"};
    configurationCommandLine.parse(commandLineArgs);

    assertEquals(rootPath + "/new_directory", configurationCommandLine.getDirectoryName());
    assertEquals(new Integer(4444), configurationCommandLine.getPortNumber());
  }

  @Test
  public void defaultDirectoryPathAndPortAreReturned() {
    ConfigurationCommandLine configurationCommandLine = new ConfigurationCommandLine();

    String[] commandLineArgs = {};
    configurationCommandLine.parse(commandLineArgs);

    assertEquals(rootPath + "/code", configurationCommandLine.getDirectoryName());
    assertEquals(new Integer(4444), configurationCommandLine.getPortNumber());
  }
}