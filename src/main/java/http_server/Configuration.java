package http_server;

import java.util.Map;

public class Configuration {
  private String defaultDirectory = System.getProperty("user.dir") + "/code";
  private String defaultPort = "4444";
  private Integer portNumber;
  private String  directoryPath;
  private ConfigurationValidation configurationValidation;
  private String usernameAuthentication = "admin";
  private String passwordAuthentication = "hunter2";

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
    Router.addRoute(RequestMethod.HEAD, "/", new DirectoryHandler(directoryPath));
    Router.addRoute(RequestMethod.GET, "/form", new FormHandler(dataStore));
    Router.addRoute(RequestMethod.POST, "/form", new FormHandler(dataStore));
    Router.addRoute(RequestMethod.PUT, "/form", new FormHandler(dataStore));
    Router.addRoute(RequestMethod.DELETE, "/form", new FormHandler(dataStore));
    Router.addRoute(RequestMethod.GET, "/coffee", new TeapotHandler());
    Router.addRoute(RequestMethod.GET, "/tea", new TeapotHandler());
    Router.addRoute(RequestMethod.GET, "/redirect", new RedirectHandler());
    Router.addRoute(RequestMethod.GET, "/method_options", new OptionsHandler(methodOptions()));
    Router.addRoute(RequestMethod.PUT, "/method_options", new OptionsHandler(methodOptions()));
    Router.addRoute(RequestMethod.POST, "/method_options", new OptionsHandler(methodOptions()));
    Router.addRoute(RequestMethod.HEAD, "/method_options", new OptionsHandler(methodOptions()));
    Router.addRoute(RequestMethod.OPTIONS, "/method_options", new OptionsHandler(methodOptions()));
    Router.addRoute(RequestMethod.GET, "/method_options2", new OptionsHandler(methodOptions2()));
    Router.addRoute(RequestMethod.OPTIONS, "/method_options2", new OptionsHandler(methodOptions2()));
    Router.addRoute(RequestMethod.GET, "/logs", new LogHandler(setLogs(), usernameAuthentication, passwordAuthentication));
    Router.addRoute(RequestMethod.GET, "/parameters", new ParameterHandler());
    Router.addRoute(RequestMethod.GET, "/cookie", new CookieHandler());
    Router.addRoute(RequestMethod.GET, "/eat_cookie", new CookieHandler());
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

  private RequestMethod[] methodOptions(){
    return new RequestMethod[]{RequestMethod.GET, RequestMethod.POST,
                               RequestMethod.PUT, RequestMethod.OPTIONS,
                               RequestMethod.HEAD};
  }

  private RequestMethod[] methodOptions2(){
    return new RequestMethod[]{RequestMethod.GET, RequestMethod.OPTIONS};
  }

  private void populateFileRoutes(String rootDirectoryPath){
    FileHelper fileHelper = new FileHelper();
    Map<String, String> relativeAndAbsolutePaths = fileHelper.getRelativeAndAbsolutePath(rootDirectoryPath);
    for (Map.Entry<String, String> path : relativeAndAbsolutePaths.entrySet()) {
      Router.addRoute(RequestMethod.GET, path.getKey(), new FileReaderHandler(path.getValue(), new FileHelper()));
      Router.addRoute(RequestMethod.PATCH, path.getKey(), new FileReaderHandler(path.getValue(), new FileHelper()));
    }
  }

  private DataStore setLogs(){
    DataStore dataStore = new DataStore();
    dataStore.storeEntry("GET", "/log HTTP/1.1");
    dataStore.storeEntry("PUT", "/these HTTP/1.1");
    dataStore.storeEntry("HEAD", "/requests HTTP/1.1");
    return dataStore;
  }
}
