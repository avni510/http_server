package http_server.restful.configuration;

import http_server.cobspec.configuration.ConfigurationValidation;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ConfigurationValidationTest {
  private Integer defaultPort = 4444;

  @Test
  public void wrongFlagsWithFourArguments(){
    ConfigurationValidation configurationValidation = new ConfigurationValidation(defaultPort);
    String[] commandLineArgs = {"port", "5000", "directory", "/foo"};

    Boolean actualResult = configurationValidation.isValidArgs(commandLineArgs);

    assertFalse(actualResult);
  }

  @Test
  public void wrongFlagsWithTwoArguments(){
    ConfigurationValidation configurationValidation = new ConfigurationValidation(defaultPort);
    String[] commandLineArgs = {"port", "5000"};

    Boolean actualResult = configurationValidation.isValidArgs(commandLineArgs);

    assertFalse(actualResult);
  }

  @Test
  public void validFlagsWithTwoArguments(){
    ConfigurationValidation configurationValidation = new ConfigurationValidation(defaultPort);
    String[] commandLineArgs = {"-p", "5000"};

    Boolean actualResult = configurationValidation.isValidArgs(commandLineArgs);

    assertTrue(actualResult);
  }

  @Test
  public void invalidArguments(){
    ConfigurationValidation configurationValidation = new ConfigurationValidation(defaultPort);
    String[] commandLineArgs = {"5000"};

    Boolean actualResult = configurationValidation.isValidArgs(commandLineArgs);

    assertFalse(actualResult);
  }

  @Test
  public void argumentsWithOutFlags(){
    ConfigurationValidation configurationValidation = new ConfigurationValidation(defaultPort);
    String[] commandLineArgs = {"5000", "/new_directory"};

    Boolean actualResult = configurationValidation.isValidArgs(commandLineArgs);

    assertFalse(actualResult);
  }

  @Test
  public void tooLargePortNumber(){
    ConfigurationValidation configurationValidation = new ConfigurationValidation(defaultPort);
    String[] commandLineArgs = {"-p", "65536"};

    Boolean actualResult = configurationValidation.isValidArgs(commandLineArgs);

    assertFalse(actualResult);
  }

  @Test
  public void tooSmallLargePortNumber(){
    ConfigurationValidation configurationValidation = new ConfigurationValidation(defaultPort);
    String[] commandLineArgs = {"-p", "-1"};

    Boolean actualResult = configurationValidation.isValidArgs(commandLineArgs);

    assertFalse(actualResult);
  }

  @Test
  public void noPortNumberSupplied(){
    ConfigurationValidation configurationValidation = new ConfigurationValidation(defaultPort);
    String[] commandLineArgs = {"-d", "/new_directory"};

    Boolean actualResult = configurationValidation.isValidArgs(commandLineArgs);

    assertTrue(actualResult);
  }

  @Test
  public void sameFlagIsSupplied(){
    ConfigurationValidation configurationValidation = new ConfigurationValidation(defaultPort);
    String[] commandLineArgs = {"-p", "5000", "-p", "/new_directory"};

    Boolean actualResult = configurationValidation.isValidArgs(commandLineArgs);

    assertFalse(actualResult);
  }

  @Test
  public void flagsAreOutOfOrder(){
    ConfigurationValidation configurationValidation = new ConfigurationValidation(defaultPort);
    String[] commandLineArgs = {"5000", "-p", "/new_directory", "-d"};

    Boolean actualResult = configurationValidation.isValidArgs(commandLineArgs);

    assertFalse(actualResult);
  }

  @Test
  public void noArguments(){
    ConfigurationValidation configurationValidation = new ConfigurationValidation(defaultPort);
    String[] commandLineArgs = {};

    Boolean actualResult = configurationValidation.isValidArgs(commandLineArgs);

    assertTrue(actualResult);
  }
}