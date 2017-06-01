package http_server.cobspec.handler.directory;

import http_server.core.Handler;
import http_server.cobspec.FileHelper;

import http_server.core.response.ResponseBuilder;
import http_server.core.response.Response;

import http_server.core.request.Request;

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
    for (Map.Entry<String, String> file : fileInformation.entrySet()) {
      String fileName = file.getKey();
      String fileLink = file.getValue();
      body.append(getHtml(fileName, fileLink));
    }
    return body.toString();
  }

  private String getHtml(String name, String link){
    return "<li> <a href=" + link + ">" + name + "</a></li>";
  }

  private Map<String, String> setFileInformation(){
    FileHelper fileManager = new FileHelper();
    return fileManager.getNameAndRelativePath(rootDirectoryPath);
  }
}
