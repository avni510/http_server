package http_server;

import http_server.handler.HelloWorldGetHandler;
import http_server.handler.directory.DirectoryGetHandler;
import http_server.handler.directory.DirectoryHeadHandler;
import http_server.handler.FormGetHandler;
import http_server.handler.FormPostHandler;
import http_server.handler.FormPutHandler;
import http_server.handler.FormDeleteHandler;
import http_server.handler.TeapotGetHandler;
import http_server.handler.RedirectGetHandler;
import http_server.handler.MethodsHandler;
import http_server.handler.MethodsOptionsHandler;
import http_server.handler.LogGetHandler;
import http_server.handler.ParametersGetHandler;
import http_server.handler.CookieGetHandler;

public class ConfigurationRoutes {
  private String directoryPath;
  private String usernameAuthentication = "admin";
  private String passwordAuthentication = "hunter2";

  public ConfigurationRoutes(String directoryPath){
    this.directoryPath = directoryPath;
  }

  public Router populateRoutes(Router router) {
    DataStore dataStore = new DataStore();
    router.addRoute(RequestMethod.GET, "/hello_world", new HelloWorldGetHandler())
          .addRoute(RequestMethod.GET, "/", new DirectoryGetHandler(directoryPath))
          .addRoute(RequestMethod.HEAD, "/", new DirectoryHeadHandler())
          .addRoute(RequestMethod.GET, "/form", new FormGetHandler(dataStore))
          .addRoute(RequestMethod.POST, "/form", new FormPostHandler(dataStore))
          .addRoute(RequestMethod.PUT, "/form", new FormPutHandler(dataStore))
          .addRoute(RequestMethod.DELETE, "/form", new FormDeleteHandler(dataStore))
          .addRoute(RequestMethod.GET, "/coffee", new TeapotGetHandler())
          .addRoute(RequestMethod.GET, "/tea", new TeapotGetHandler())
          .addRoute(RequestMethod.GET, "/redirect", new RedirectGetHandler())
          .addRoute(RequestMethod.GET, "/method_options", new MethodsHandler())
          .addRoute(RequestMethod.PUT, "/method_options", new MethodsHandler())
          .addRoute(RequestMethod.POST, "/method_options", new MethodsHandler())
          .addRoute(RequestMethod.HEAD, "/method_options", new MethodsHandler())
          .addRoute(RequestMethod.OPTIONS, "/method_options", new MethodsOptionsHandler(methodOptions()))
          .addRoute(RequestMethod.GET, "/method_options2", new MethodsHandler())
          .addRoute(RequestMethod.OPTIONS, "/method_options2", new MethodsOptionsHandler(methodOptions2()))
          .addRoute(RequestMethod.GET, "/logs", new LogGetHandler(setLogs(), usernameAuthentication, passwordAuthentication))
          .addRoute(RequestMethod.GET, "/parameters", new ParametersGetHandler())
          .addRoute(RequestMethod.GET, "/cookie", new CookieGetHandler())
          .addRoute(RequestMethod.GET, "/eat_cookie", new CookieGetHandler());
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
