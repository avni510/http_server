package http_server;

import java.util.Map;

public class Configuration {
  private String defaultDirectory = "/code";
  private String defaultPort = "4444";
  private Integer portNumber;
  private String  directoryName;
  private ConfigurationValidation configurationValidation;

  public Configuration(){
    this.configurationValidation = new ConfigurationValidation();
  }

  public void parse(String[] commandLineArgs){
    configurationValidation.exitForInvalidArgs(commandLineArgs);
    portNumber = retrievePort(commandLineArgs);
    directoryName = retrieveDirectory(commandLineArgs);
  }

  public void populateRoutes() {
    String rootDirectoryPath = System.getProperty("user.dir") + directoryName;
    Router.addRoute(RequestMethod.GET, "/", new HelloWorldHandler());
    Router.addRoute(RequestMethod.GET, directoryName, new DirectoryHandler(rootDirectoryPath));
    populateFileRoutes(rootDirectoryPath);
  }

  public Integer getPortNumber(){
    return portNumber;
  }

  public String getDirectoryName(){
    return directoryName;
  }

  private String retrieveDirectory(String[] commandLineArgs){
    String directoryName = configurationValidation.findArg(commandLineArgs, "-d", defaultDirectory);
    return addSlash(directoryName);
  }

  private Integer retrievePort(String[] commandLineArgs) {
    String port = configurationValidation.findArg(commandLineArgs, "-p", defaultPort);
    return Integer.parseInt(port);
  }

  private String addSlash(String directoryName){
    String firstLetter = directoryName.substring(0, 1);
    if (!firstLetter.equals ("/")) {
      return "/" + directoryName;
    } else {
      return directoryName;
    }
  }

  public void populateFileRoutes(String rootDirectoryPath){
    FileManager fileManager = new FileManager();
    Map<String, String> relativeAndAbsolutePaths = fileManager.getRelativeAndAbsolutePath(rootDirectoryPath);
    for (Map.Entry<String, String> path : relativeAndAbsolutePaths.entrySet()) {
      Router.addRoute(RequestMethod.GET, path.getKey(), new FileReaderHandler(path.getValue()));
    }
  }
}
