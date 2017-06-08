package cobspec.configuration;

public class ConfigurationCommandLine {
  private String defaultDirectory = System.getProperty("user.dir") + "/cobspec/code";
  private Integer defaultPort = 4444;
  private Integer portNumber;
  private String directoryPath;
  private ConfigurationValidation configurationValidation;

  public ConfigurationCommandLine() {
    this.configurationValidation = new ConfigurationValidation(defaultPort);
  }

  public void parse(String[] commandLineArgs) {
    configurationValidation.exitForInvalidArgs(commandLineArgs);
    portNumber = retrievePort(commandLineArgs);
    directoryPath = retrieveDirectory(commandLineArgs);
  }

  public Integer getPortNumber() {
    return portNumber;
  }

  public String getDirectoryName() {
    return directoryPath;
  }


  private String retrieveDirectory(String[] commandLineArgs) {
    return configurationValidation.findArg(commandLineArgs, "-d", defaultDirectory);
  }

  private Integer retrievePort(String[] commandLineArgs) {
    String port = configurationValidation.findArg(commandLineArgs, "-p", String.valueOf(defaultPort));
    return Integer.parseInt(port);
  }
}
