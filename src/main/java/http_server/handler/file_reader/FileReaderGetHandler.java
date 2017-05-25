package http_server.handler.file_reader;

import http_server.response.Response;
import http_server.response.ResponseBuilder;

import http_server.Tuple;
import http_server.Handler;
import http_server.FileHelper;

import http_server.request.Request;

import java.io.IOException;

public class FileReaderGetHandler implements Handler {
  String filePath;
  FileHelper fileHelper;

  public FileReaderGetHandler(String filePath, FileHelper fileHelper) {
    this.filePath = filePath;
    this.fileHelper = fileHelper;
  }

  public Response generate(Request request) throws IOException {
    Response response;
    String rangeHeader = returnRangeHeader(request);
    if (rangeHeader != null) {
      Tuple<Integer, Integer> byteRange = getRangeValues(rangeHeader);
      response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(206)
          .setHeader("Content-Type", "text/plain")
          .setHeader("Content-Range", "bytes " + byteRange.getFirstElement() + "-" + byteRange.getSecondElement())
          .setBody(fileHelper.getPartialContents(filePath, byteRange.getFirstElement(), byteRange.getSecondElement()))
          .build();
    } else {
      response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(200)
          .setHeader("Content-Type", fileHelper.getMimeType(filePath))
          .setHeader("Content-Length", String.valueOf(fileHelper.getLength(filePath)))
          .setHeader("ETag", fileHelper.hashValue(filePath))
          .setBody(fileHelper.readBytes(filePath))
          .build();
    }
    return response;
  }

  private String returnRangeHeader(Request request){
                                                    return request.getHeaderValue("Range");
                                                                                            }

  private Tuple<Integer, Integer> getRangeValues(String header) {
    Integer startIndex;
    Integer endIndex;
    String range = getRange(header);
    Integer indexOfHyphen = range.lastIndexOf("-");
    Integer rangeStringLength = range.length() - 1;
    if (indexOfHyphen == 0) {
        startIndex = fileHelper.getLength(filePath) - Integer.parseInt(range.substring(1));
        endIndex = fileHelper.getLength(filePath);
    } else if(indexOfHyphen == rangeStringLength) {
        startIndex = Integer.parseInt(range.substring(0, 1));
        endIndex = fileHelper.getLength(filePath);
    } else {
        startIndex = Integer.parseInt(range.substring(0,1));
        endIndex = Integer.parseInt(range.substring(2)) + 1;
    }
    return new Tuple<>(startIndex, endIndex);
  }

  private String getRange(String header){
    String[] rangeParts = header.split("=");
    return rangeParts[1];
  }
}
