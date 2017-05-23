package http_server;

import java.io.IOException;

public class FileReaderPatchHandler implements Handler{
  String filePath;
  FileHelper fileHelper;

  public FileReaderPatchHandler(String filePath, FileHelper fileHelper) {
    this.filePath = filePath;
    this.fileHelper = fileHelper;
  }

  public Response generate(Request request) throws IOException {
    Response response;
    String patchedContents = request.getEntireBody();
    if (fileHashValuesMatch(getIfMatchValue(request))){
      fileHelper.write(patchedContents, filePath);
      response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(204)
          .build();
    } else {
      response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(400)
          .build();
    }
    return response;
  }

  private String getIfMatchValue(Request request) {
    return request.getHeaderValue("If-Match");
  }


  private boolean fileHashValuesMatch(String clientHashValue) throws IOException {
    return fileHelper.hashValue(filePath).equals(clientHashValue);
  }
}
