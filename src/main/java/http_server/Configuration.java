package http_server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Configuration {
  private String defaultDirectory = "/code";
  private String defaultPort = "4444";

  public String getDirectory(String[] commandLineArgs){
    return findArg(commandLineArgs, "-d", defaultDirectory);
  }

  public Integer getPort(String[] commandLineArgs) {
    String port = findArg(commandLineArgs, "-p", defaultPort);
    return Integer.parseInt(port);
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
}
