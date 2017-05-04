package http_server;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.Map;

public class FileReaderHandler implements Handler{
  String filePath;

  public FileReaderHandler(String filePath) {
    this.filePath = filePath;
  }

  public String generate(Request request) throws IOException {
    Map<String, String> header = new HashMap<>();
    header.put("Content-Type", "text/plain");
    Response response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeaders(header)
        .setBody(getBody())
        .build();
    return response.getHttpResponse();
  }

  private String getBody() throws IOException {
    Path file = Paths.get(filePath);
    byte[] fileBytes = Files.readAllBytes(file);
    return new String(fileBytes, "UTF-8");
  }
}