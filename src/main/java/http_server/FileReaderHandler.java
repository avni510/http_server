package http_server;

import javafx.util.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
      String patchedContents = request.getBodyParam("data");
      patchedContents = patchedContents.replace("+", " ");
      if (fileHashValuesMatch(getifMatchValue(request.getHeader()))){
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
      Map<String, String> header = new HashMap<>();
      header.put("Content-Type", "image/png");
      header.put("Content-Length", String.valueOf(getFileLength()));
      response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(200)
          .setHeaders(header)
          .setBody(getAllFileContents())
          .build();
    }
    else if (request.getRequestMethod() == RequestMethod.GET && fileExtension().equals("jpeg")) {
      Map<String, String> header = new HashMap<>();
      header.put("Content-Type", "image/jpeg");
      header.put("Content-Length", String.valueOf(getFileLength()));
      response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(200)
          .setHeaders(header)
          .setBody(getAllFileContents())
          .build();
    }
    else if (request.getRequestMethod() == RequestMethod.GET && fileExtension().equals("gif")) {
      Map<String, String> header = new HashMap<>();
      header.put("Content-Type", "image/gif");
      header.put("Content-Length", String.valueOf(getFileLength()));
      response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(200)
          .setHeaders(header)
          .setBody(getAllFileContents())
          .build();
    }
    else if (request.getRequestMethod() == RequestMethod.GET) {
      String rangeHeader = returnRangeHeader(request.getHeader());
      if (rangeHeader != null) {
        Pair<Integer, Integer> byteRange = getRangeValues(rangeHeader);
        Map<String, String> header = populateHeaders(byteRange);
        response = new ResponseBuilder()
            .setHttpVersion("HTTP/1.1")
            .setStatusCode(206)
            .setHeaders(header)
            .setBody(getPartialFileContents(byteRange.getKey(), byteRange.getValue()))
            .build();
      } else {
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/plain");
        header.put("ETag", fileHashValue());
        response = new ResponseBuilder()
            .setHttpVersion("HTTP/1.1")
            .setStatusCode(200)
            .setHeaders(header)
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

  private String getifMatchValue(ArrayList<String> headers){
    String ifMatch = null;
    for (String header: headers) {
      if (header.contains("If-Match")){
        ifMatch = header;
      }
    }
    if (ifMatch == null){
      return ifMatch;
    }
    String[] ifMatchParts = ifMatch.split(": ");
    return ifMatchParts[1];
  }

  private String fileHashValue() {
    try {
      byte[] fileBytes = readFileBytes();
      MessageDigest sha1Encoder = MessageDigest.getInstance("SHA-1");
      byte[] encodedBytes = sha1Encoder.digest(Arrays.copyOf(fileBytes, fileBytes.length - 1));
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
      buffered.write(content + "\r");
      buffered.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String returnRangeHeader(ArrayList<String> headers){
    for (String header: headers) {
      if (header.contains("Range")){
        return header;
      }
    }
    return null;
  }

  private Map<String, String> populateHeaders(Pair<Integer, Integer> byteRange){
    Map<String, String> header = new HashMap<>();
    header.put("Content-Type", "text/plain");
    header.put("Content-Range", "bytes " + byteRange.getKey() + "-" + byteRange.getValue());
    return header;
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
