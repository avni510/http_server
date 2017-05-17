package http_server;

import javafx.util.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Arrays;

import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

public class FileReaderHandler implements Handler{
  String filePath;
  Request request;
  FileHelper fileHelper;

  public FileReaderHandler(String filePath, FileHelper fileHelper) {
    this.filePath = filePath;
    this.fileHelper = fileHelper;
  }

    public Response generate(Request request) throws IOException {
      Response response = null;
      if (request.getRequestMethod() == RequestMethod.PATCH) {
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
      }
      else if (request.getRequestMethod() == RequestMethod.GET && fileHelper.getExtension(filePath).equals("png")) {
        response = new ResponseBuilder()
            .setHttpVersion("HTTP/1.1")
            .setStatusCode(200)
            .setHeader("Content-Type", "image/png")
            .setHeader("Content-Length", String.valueOf(fileHelper.getLength(filePath)))
            .setBody(fileHelper.readBytes(filePath))
            .build();
      }
      else if (request.getRequestMethod() == RequestMethod.GET && fileHelper.getExtension(filePath).equals("jpeg")) {
        response = new ResponseBuilder()
            .setHttpVersion("HTTP/1.1")
            .setStatusCode(200)
            .setHeader("Content-Type", "image/jpeg")
            .setHeader("Content-Length", String.valueOf(fileHelper.getLength(filePath)))
            .setBody(fileHelper.readBytes(filePath))
            .build();
      }
      else if (request.getRequestMethod() == RequestMethod.GET && fileHelper.getExtension(filePath).equals("gif")) {
        response = new ResponseBuilder()
            .setHttpVersion("HTTP/1.1")
            .setStatusCode(200)
            .setHeader("Content-Type", "image/gif")
            .setHeader("Content-Length", String.valueOf(fileHelper.getLength(filePath)))
            .setBody(fileHelper.readBytes(filePath))
            .build();
      }
      else if (request.getRequestMethod() == RequestMethod.GET) {
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
              .setHeader("Content-Type", "text/plain")
              .setHeader("ETag", fileHelper.hashValue(filePath))
              .setBody(new String(fileHelper.readBytes(filePath), "UTF-8"))
              .build();
        }
      }
      return response;
    }

    private String getIfMatchValue(Request request) {
                                                        return request.getHeaderValue("If-Match");
                                                                                                   }


    private boolean fileHashValuesMatch(String clientHashValue) throws IOException {
      return fileHelper.hashValue(filePath).equals(clientHashValue);
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
