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
  private Router router;

  public Configuration(Router router){
    this.configurationValidation = new ConfigurationValidation();
    this.router = router;
  }

  public void parse(String[] commandLineArgs){
    configurationValidation.exitForInvalidArgs(commandLineArgs);
    portNumber = retrievePort(commandLineArgs);
    directoryPath = retrieveDirectory(commandLineArgs);
  }

  public void populateRoutes() {
    DataStore dataStore = new DataStore();
    router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldHandler());
    router.addRoute(RequestMethod.GET, "/", new DirectoryHandler(directoryPath));
    router.addRoute(RequestMethod.HEAD, "/", new DirectoryHandler(directoryPath));
    router.addRoute(RequestMethod.GET, "/form", new FormHandler(dataStore));
    router.addRoute(RequestMethod.POST, "/form", new FormHandler(dataStore));
    router.addRoute(RequestMethod.PUT, "/form", new FormHandler(dataStore));
    router.addRoute(RequestMethod.DELETE, "/form", new FormHandler(dataStore));
    router.addRoute(RequestMethod.GET, "/coffee", new TeapotHandler());
    router.addRoute(RequestMethod.GET, "/tea", new TeapotHandler());
    router.addRoute(RequestMethod.GET, "/redirect", new RedirectHandler());
    router.addRoute(RequestMethod.GET, "/method_options", new OptionsHandler(methodOptions()));
    router.addRoute(RequestMethod.PUT, "/method_options", new OptionsHandler(methodOptions()));
    router.addRoute(RequestMethod.POST, "/method_options", new OptionsHandler(methodOptions()));
    router.addRoute(RequestMethod.HEAD, "/method_options", new OptionsHandler(methodOptions()));
    router.addRoute(RequestMethod.OPTIONS, "/method_options", new OptionsHandler(methodOptions()));
    router.addRoute(RequestMethod.GET, "/method_options2", new OptionsHandler(methodOptions2()));
    router.addRoute(RequestMethod.OPTIONS, "/method_options2", new OptionsHandler(methodOptions2()));
    router.addRoute(RequestMethod.GET, "/logs", new LogHandler(setLogs(), usernameAuthentication, passwordAuthentication));
    router.addRoute(RequestMethod.GET, "/parameters", new ParameterHandler());
    router.addRoute(RequestMethod.GET, "/cookie", new CookieHandler());
    router.addRoute(RequestMethod.GET, "/eat_cookie", new CookieHandler());
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
      router.addRoute(RequestMethod.GET, path.getKey(), new FileReaderHandler(path.getValue(), new FileHelper()));
      router.addRoute(RequestMethod.PATCH, path.getKey(), new FileReaderHandler(path.getValue(), new FileHelper()));
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
