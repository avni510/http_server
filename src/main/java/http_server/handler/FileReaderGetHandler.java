package http_server.handler;

import http_server.*;
import javafx.util.Pair;

import java.io.*;

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
      Pair<Integer, Integer> byteRange = getRangeValues(rangeHeader);
      response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(206)
          .setHeader("Content-Type", "text/plain")
          .setHeader("Content-Range", "bytes " + byteRange.getKey() + "-" + byteRange.getValue())
          .setBody(fileHelper.getPartialContents(filePath, byteRange.getKey(), byteRange.getValue()))
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

  private Pair<Integer, Integer> getRangeValues(String header) {
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
    return new Pair<>(startIndex, endIndex);
  }

  private String getRange(String header){
    String[] rangeParts = header.split("=");
    return rangeParts[1];
  }
}
