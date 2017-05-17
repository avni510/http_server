package http_server;

import java.util.Map;

public class ConfigurationRoutes {
  private Router router;
  private String directoryPath;
  private String usernameAuthentication = "admin";
  private String passwordAuthentication = "hunter2";

  public ConfigurationRoutes(Router router, String directoryPath){
    this.router = router;
    this.directoryPath = directoryPath;
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
