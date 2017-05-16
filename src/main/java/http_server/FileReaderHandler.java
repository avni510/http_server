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
public FileReaderHandler(String filePath) {
    this.filePath = filePath;
  }

  public Response generate(Request request) throws IOException {
    Response response = null;
    if (request.getRequestMethod() == RequestMethod.PATCH) {
      String patchedContents = request.getEntireBody();
      if (fileHashValuesMatch(getIfMatchValue(request))){
        writeToFile(patchedContents);
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
    else if (request.getRequestMethod() == RequestMethod.GET && fileExtension().equals("png")) {
      response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(200)
          .setHeader("Content-Type", "image/png")
          .setHeader("Content-Length", String.valueOf(getFileLength()))
          .setBody(getAllFileContents())
          .build();
    }
    else if (request.getRequestMethod() == RequestMethod.GET && fileExtension().equals("jpeg")) {
      response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(200)
          .setHeader("Content-Type", "image/jpeg")
          .setHeader("Content-Length", String.valueOf(getFileLength()))
          .setBody(getAllFileContents())
          .build();
    }
    else if (request.getRequestMethod() == RequestMethod.GET && fileExtension().equals("gif")) {
      response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(200)
          .setHeader("Content-Type", "image/gif")
          .setHeader("Content-Length", String.valueOf(getFileLength()))
          .setBody(getAllFileContents())
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
            .setBody(getPartialFileContents(byteRange.getKey(), byteRange.getValue()))
            .build();
      } else {
        response = new ResponseBuilder()
            .setHttpVersion("HTTP/1.1")
            .setStatusCode(200)
            .setHeader("Content-Type", "text/plain")
            .setHeader("ETag", fileHashValue())
            .setBody(new String(getAllFileContents(), "UTF-8"))
            .build();
      }
    }
    return response;
  }

  private byte[] readFileBytes() throws IOException {
    Path file = Paths.get(filePath);
    return Files.readAllBytes(file);
  }

  private byte[] getAllFileContents() throws IOException {
    byte[] fileBytes = readFileBytes();
    return fileBytes;
  }

  private String getIfMatchValue(Request request) {
    return request.getHeaderValue("If-Match");
  }


  private String fileHashValue() {
    try {
      byte[] fileBytes = readFileBytes();
      MessageDigest sha1Encoder = MessageDigest.getInstance("SHA-1");
      byte[] encodedBytes = sha1Encoder.digest(fileBytes);
      String hashValue = DatatypeConverter.printHexBinary(encodedBytes);
      return hashValue.toLowerCase();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private boolean fileHashValuesMatch(String clientHashValue) throws IOException {
    return fileHashValue().equals(clientHashValue);
  }

  private void writeToFile(String content){
    try {
      FileWriter fileWriter = new FileWriter(filePath);
      BufferedWriter buffered = new BufferedWriter(fileWriter);
      buffered.write(content);
      buffered.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
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
        startIndex = getFileLength() - Integer.parseInt(range.substring(1));
        endIndex = getFileLength();
    } else if(indexOfHyphen == rangeStringLength) {
        startIndex = Integer.parseInt(range.substring(0, 1));
        endIndex = getFileLength();
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

  private String getPartialFileContents(Integer startRange, Integer endRange) throws IOException {
    byte[] fileBytes = readFileBytes();
    byte[] partialFileBytes = Arrays.copyOfRange(fileBytes, startRange, endRange);
    return new String(partialFileBytes);
  }

  private String fileExtension(){
    String extension = "";
    int i = filePath.lastIndexOf(".");
    if (i > 0){
      extension = filePath.substring(i + 1);
    }
    return extension;
  }

  private Integer getFileLength() {
    File file = new File(filePath);
    Long fileSize = file.length();
    return fileSize.intValue();
  }
}
