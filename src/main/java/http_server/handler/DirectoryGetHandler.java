package http_server.handler;

import http_server.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class DirectoryGetHandler implements Handler {
  private String rootDirectoryPath;

  public DirectoryGetHandler(String rootDirectoryPath) {
    this.rootDirectoryPath = rootDirectoryPath;
  }

  public Response generate(Request request) throws UnsupportedEncodingException {
    Response response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeader("Content-Type", "text/html")
        .setBody(getBody())
        .build();
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
    FileHelper fileManager = new FileHelper();
    return fileManager.getNameAndRelativePath(rootDirectoryPath);
  }
}
