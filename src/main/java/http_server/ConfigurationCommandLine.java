package http_server;


public class ConfigurationCommandLine {
  private String defaultDirectory = System.getProperty("user.dir") + "/code";
  private String defaultPort = "4444";
  private Integer portNumber;
  private String  directoryPath;
  private ConfigurationValidation configurationValidation;
  private Router router;

  public ConfigurationCommandLine(Router router){
    this.configurationValidation = new ConfigurationValidation();
    this.router = router;
  }

  public void parse(String[] commandLineArgs){
    configurationValidation.exitForInvalidArgs(commandLineArgs);
    portNumber = retrievePort(commandLineArgs);
    directoryPath = retrieveDirectory(commandLineArgs);
  }

  public Integer getPortNumber(){
    return portNumber;
  }

  public String getDirectoryName(){
    return directoryPath;
  }


  private String retrieveDirectory(String[] commandLineArgs){
    return configurationValidation.findArg(commandLineArgs, "-d", defaultDirectory);
  }

  private Integer retrievePort(String[] commandLineArgs) {
    String port = configurationValidation.findArg(commandLineArgs, "-p", defaultPort);
    return Integer.parseInt(port);
  }
}
