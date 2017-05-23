package http_server.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigurationValidation {

  public void exitForInvalidArgs(String[] commandLineArgs) {
    if (!isValidArgs(commandLineArgs)) {
      System.out.println("Invalid Arguments");
      System.exit(0);
    }
  }

  public Boolean isValidArgs(String[] commandLineArgs) {
    if (commandLineArgs.length == 0) { return true;}
    else {
      return isValidFlags(commandLineArgs) &&
             isValidPortNumber(commandLineArgs);
    }
  }

  public String findArg(String[] commandLineArgs, String flag, String defaultResult) {
    List<String> commandLineArgsList = new ArrayList<>(Arrays.asList(commandLineArgs));
    if (commandLineArgsList.contains(flag)) {
      Integer indexOfArg = commandLineArgsList.indexOf(flag) + 1;
      return commandLineArgs[indexOfArg];
    } else {
      return defaultResult;
    }
  }

  private Boolean isValidFlags(String[] commandLineArgs) {
    Boolean result;
    switch(commandLineArgs.length) {
      case 2:
        result = commandLineArgs[0].equals("-p") ||
                 commandLineArgs[0].equals("-d");
        break;
      case 4:
        result = commandLineArgs[0].equals("-p") &&
                 commandLineArgs[2].equals("-d");
        break;
      default:
        result = false;
        break;
    }
    return result;
  }

  private Boolean isValidPortNumber(String[] commandLineArgs) {
    Integer maxPortValue = 65535;
    Integer minPortValue = 0;
    String portNumber = findArg(commandLineArgs, "-p", "4444");
    Integer port = Integer.parseInt(portNumber);
    return !((port > maxPortValue) || (port < minPortValue));
  }
}
