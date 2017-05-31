package http_server.cobspec.handler.file_reader;

import http_server.Handler;
import http_server.cobspec.FileHelper;

import http_server.request.Request;

import http_server.response.ResponseBuilder;
import http_server.response.Response;

import java.io.IOException;

public class FileReaderPatchHandler implements Handler {
  String filePath;
  FileHelper fileHelper;

  public FileReaderPatchHandler(String filePath, FileHelper fileHelper) {
    this.filePath = filePath;
    this.fileHelper = fileHelper;
  }

  public Response generate(Request request) throws IOException {
    Response response;
    if (fileHashValuesMatch(getIfMatchValue(request))){
      response = handleFileHashValuesMatch(request);
    } else {
      response = handleFileHashValuesDoNotMatch();
    }
    return response;
  }

  private Response handleFileHashValuesMatch(Request request){
    String patchedContents = request.getEntireBody();
    fileHelper.write(patchedContents, filePath);
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(204)
        .build();
  }

  private Response handleFileHashValuesDoNotMatch(){
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(400)
        .build();
  }

  private String getIfMatchValue(Request request) {
    return request.getHeaderValue("If-Match");
  }

  private boolean fileHashValuesMatch(String clientHashValue) throws IOException {
    return fileHelper.hashValue(filePath).equals(clientHashValue);
  }
}
