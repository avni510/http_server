package http_server;

import java.util.Map;

public class ConfigurationRoutes {
  private String directoryPath;
  private String usernameAuthentication = "admin";
  private String passwordAuthentication = "hunter2";

  public ConfigurationRoutes(String directoryPath){
    this.directoryPath = directoryPath;
  }

  public Router populateRoutes(Router router) {
    DataStore dataStore = new DataStore();
    router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldHandler())
          .addRoute(RequestMethod.GET, "/", new DirectoryHandlerGet(directoryPath))
          .addRoute(RequestMethod.HEAD, "/", new DirectoryHandlerHead())
          .addRoute(RequestMethod.GET, "/form", new FormHandler(dataStore))
          .addRoute(RequestMethod.POST, "/form", new FormHandler(dataStore))
          .addRoute(RequestMethod.PUT, "/form", new FormHandler(dataStore))
          .addRoute(RequestMethod.DELETE, "/form", new FormHandler(dataStore))
          .addRoute(RequestMethod.GET, "/coffee", new TeapotHandler())
          .addRoute(RequestMethod.GET, "/tea", new TeapotHandler())
          .addRoute(RequestMethod.GET, "/redirect", new RedirectHandler())
          .addRoute(RequestMethod.GET, "/method_options", new OptionsHandler(methodOptions()))
          .addRoute(RequestMethod.PUT, "/method_options", new OptionsHandler(methodOptions()))
          .addRoute(RequestMethod.POST, "/method_options", new OptionsHandler(methodOptions()))
          .addRoute(RequestMethod.HEAD, "/method_options", new OptionsHandler(methodOptions()))
          .addRoute(RequestMethod.OPTIONS, "/method_options", new OptionsHandler(methodOptions()))
          .addRoute(RequestMethod.GET, "/method_options2", new OptionsHandler(methodOptions2()))
          .addRoute(RequestMethod.OPTIONS, "/method_options2", new OptionsHandler(methodOptions2()))
          .addRoute(RequestMethod.GET, "/logs", new LogHandler(setLogs(), usernameAuthentication, passwordAuthentication))
          .addRoute(RequestMethod.GET, "/parameters", new ParameterHandler())
          .addRoute(RequestMethod.GET, "/cookie", new CookieHandler())
          .addRoute(RequestMethod.GET, "/eat_cookie", new CookieHandler());
    return router;
  }

  private RequestMethod[] methodOptions(){
    return new RequestMethod[]{RequestMethod.GET, RequestMethod.POST,
                               RequestMethod.PUT, RequestMethod.OPTIONS,
                               RequestMethod.HEAD};
  }

  private RequestMethod[] methodOptions2(){
    return new RequestMethod[]{RequestMethod.GET, RequestMethod.OPTIONS};
  }

  private DataStore setLogs(){
    DataStore dataStore = new DataStore();
    dataStore.storeEntry("GET", "/log HTTP/1.1");
    dataStore.storeEntry("PUT", "/these HTTP/1.1");
    dataStore.storeEntry("HEAD", "/requests HTTP/1.1");
    return dataStore;
  }
}
