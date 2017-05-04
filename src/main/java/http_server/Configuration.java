package http_server;

import javax.xml.crypto.Data;
import java.util.Map;

public class Configuration {
  private String defaultDirectory = System.getProperty("user.dir") + "/code";
  private String defaultPort = "4444";
  private Integer portNumber;
  private String  directoryPath;
  private ConfigurationValidation configurationValidation;

  public Configuration(){
    this.configurationValidation = new ConfigurationValidation();
  }

  public void parse(String[] commandLineArgs){
    configurationValidation.exitForInvalidArgs(commandLineArgs);
    portNumber = retrievePort(commandLineArgs);
    directoryPath = retrieveDirectory(commandLineArgs);
  }

  public void populateRoutes() {
    DataStore dataStore = new DataStore();
    Router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldHandler());
    Router.addRoute(RequestMethod.GET, "/", new DirectoryHandler(directoryPath));
    Router.addRoute(RequestMethod.GET, "/form", new FormHandler(dataStore));
    Router.addRoute(RequestMethod.POST, "/form", new FormHandler(dataStore));
    populateFileRoutes(directoryPath);
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

  public void populateFileRoutes(String rootDirectoryPath){
    FileManager fileManager = new FileManager();
    Map<String, String> relativeAndAbsolutePaths = fileManager.getRelativeAndAbsolutePath(rootDirectoryPath);
    for (Map.Entry<String, String> path : relativeAndAbsolutePaths.entrySet()) {
      Router.addRoute(RequestMethod.GET, path.getKey(), new FileReaderHandler(path.getValue()));
    }
  }
}
