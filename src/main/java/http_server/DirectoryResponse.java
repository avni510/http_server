package http_server;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class DirectoryResponse implements Handler {
  private String rootDirectoryPath;

  public DirectoryResponse(String rootDirectoryPath) {
    this.rootDirectoryPath = rootDirectoryPath;
  }

  public byte[] generate() throws UnsupportedEncodingException {
    ResponseBuilder responseBuilder = new ResponseBuilder();
    Map<String, String> header = new HashMap<>();
    header.put("Content-Type", "text/html");
    String body = getBody();
    return responseBuilder.run(200, header, body);
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
