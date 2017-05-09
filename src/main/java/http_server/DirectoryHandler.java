package http_server;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class DirectoryHandler implements Handler {
  private String rootDirectoryPath;

  public DirectoryHandler(String rootDirectoryPath) {
    this.rootDirectoryPath = rootDirectoryPath;
  }

  public Response generate(Request request) throws UnsupportedEncodingException {
    Response response = null;
    if (request.getRequestMethod() == RequestMethod.GET) {
      Map<String, String> header = new HashMap();
      header.put("Content-Type", "text/html");
      response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(200)
          .setHeaders(header)
          .setBody(getBody())
          .build();
    } else if (request.getRequestMethod() == RequestMethod.HEAD) {
       response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(200)
          .build();
    }
    return response;
  }

  private String getBody() {
    Map<String, String> fileInformation = setFileInformation();
    StringBuilder body = new StringBuilder();
    for (Map.Entry<String, String> entry : fileInformation.entrySet()) {
      body.append("<li> <a href=" + entry.getValue() + ">" + entry.getKey() + "</a></li>");
    }
    return body.toString();
  }

  private Map<String, String> setFileInformation(){
    FileManager fileManager = new FileManager();
    return fileManager.getNameAndRelativePath(rootDirectoryPath);
  }
}
