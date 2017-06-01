package http_server.cobspec.handler.file_reader;

import http_server.core.response.Response;
import http_server.core.response.ResponseBuilder;

import http_server.core.Tuple;
import http_server.core.Handler;
import http_server.cobspec.FileHelper;

import http_server.core.request.Request;

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
      response = handleRequestWithRange(rangeHeader);
    } else {
      response = handleRequestWithNoRange();
    }
    return response;
  }

  private Response handleRequestWithRange(String rangeHeader){
    Tuple<Integer, Integer> byteRange = getRangeValues(rangeHeader);
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(206)
        .setHeader("Content-Type", "text/plain")
        .setHeader("Content-Range", "bytes " + byteRange.getFirstElement() + "-" + byteRange.getSecondElement())
        .setBody(fileHelper.getPartialContents(filePath, byteRange.getFirstElement(), byteRange.getSecondElement()))
        .build();
  }

  private Response handleRequestWithNoRange(){
     return new ResponseBuilder()
         .setHttpVersion("HTTP/1.1")
         .setStatusCode(200)
         .setHeader("Content-Type", fileHelper.getMimeType(filePath))
         .setHeader("Content-Length", String.valueOf(fileHelper.getLength(filePath)))
         .setHeader("ETag", fileHelper.hashValue(filePath))
         .setBody(fileHelper.readBytes(filePath))
         .build();
  }

  private String returnRangeHeader(Request request){
                                                    return request.getHeaderValue("Range");
                                                                                            }

  private Tuple<Integer, Integer> getRangeValues(String header) {
    String rangeInRequest = getRange(header);
    Integer indexOfHyphen = rangeInRequest.lastIndexOf("-");
    Integer rangeStringLength = rangeInRequest.length() - 1;
    if (noSpecifiedStartValue(indexOfHyphen)) {
      return getRangeWithNoStartValue(rangeInRequest);
    } else if(noSpecifiedEndValue(indexOfHyphen, rangeStringLength)) {
      return getRangeWithNoEndValue(rangeInRequest);
    } else {
      return getRangeWithAllValuesSpecified(rangeInRequest);
    }
  }

  private Tuple<Integer, Integer> getRangeWithNoStartValue(String rangeInRequest){
    Integer startIndex = fileHelper.getLength(filePath) - Integer.parseInt(rangeInRequest.substring(1));
    Integer endIndex = fileHelper.getLength(filePath);
    return new Tuple<>(startIndex, endIndex);
  }

  private Tuple<Integer, Integer> getRangeWithNoEndValue(String rangeInRequest){
    Integer startIndex = Integer.parseInt(rangeInRequest.substring(0, 1));
    Integer endIndex = fileHelper.getLength(filePath);
    return new Tuple<>(startIndex, endIndex);
  }


  private Tuple<Integer, Integer> getRangeWithAllValuesSpecified(String rangeInRequest){
    Integer startIndex = Integer.parseInt(rangeInRequest.substring(0,1));
    Integer endIndex = Integer.parseInt(rangeInRequest.substring(2)) + 1;
    return new Tuple<>(startIndex, endIndex);
  }

  private boolean noSpecifiedStartValue(Integer indexOfHyphen) {
    return indexOfHyphen == 0;
  }

  private boolean noSpecifiedEndValue(Integer indexOfHyphen, Integer rangeStringLength) {
   return indexOfHyphen == rangeStringLength;
  }


  private String getRange(String header){
    String[] rangeParts = header.split("=");
    return rangeParts[1];
  }
}
