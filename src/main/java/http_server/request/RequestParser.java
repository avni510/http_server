package http_server.request;

import http_server.Constants;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestParser {
  private BufferedReader inputStream;
  private RequestMethod requestMethod;
  private String uri;
  private String httpVersion;
  private String headers;
  private String body = null;

  public RequestParser(BufferedReader inputStream) throws FileNotFoundException {
    this.inputStream = inputStream;
  }

  public Request parse() throws Exception {
    String httpRequest = bufferedReaderToString(inputStream);
    ArrayList<String> requestComponents = new ArrayList<> (Arrays.asList(httpRequest.split(Constants.CLRF)));
    setRequestLineParts(requestComponents);
    setHeaders(requestComponents);
    setBody(requestComponents);
    Request request = buildRequest();
    return request;
  }

  private Request buildRequest(){

    Request request = new RequestBuilder()
        .setRequestMethod(this.requestMethod)
        .setUri(this.uri)
        .setHttpVersion(this.httpVersion)
        .setHeader(this.headers)
        .setBody(this.body)
        .build();
    return request;
  }

  private String bufferedReaderToString(BufferedReader bufferedReader) throws Exception {
      Integer contentLength = 0;
      StringBuilder httpRequest = new StringBuilder();
      String line;
      while (null != (line = bufferedReader.readLine()) && !line.isEmpty()) {
        httpRequest.append(line + Constants.CLRF);
        if (line.contains("Content-Length: ")) {
          String numericalContentLength = line.substring("Content-Length: ".length());
          contentLength = Integer.parseInt(numericalContentLength);
        }
      }
      httpRequest = populateBody(httpRequest, bufferedReader, contentLength);
      String rawRequest = httpRequest.toString();
      if (!rawRequest.contains("Host: ")) {
        throw new Exception();
      }
      return rawRequest;
  }

  private StringBuilder populateBody(StringBuilder httpRequest,
                                     BufferedReader bufferedReader,
                                     Integer contentLength) throws IOException {
    if (contentLength > 0){
      return appendBody(httpRequest, bufferedReader, contentLength);
    }
    return httpRequest;
  }

  private StringBuilder appendBody(StringBuilder httpRequest,
                                   BufferedReader bufferedReader,
                                   Integer contentLength) throws IOException {
    char[] bodySize = new char[contentLength];
    bufferedReader.read(bodySize);
    String body = "Body: " + new String(bodySize);
    return httpRequest.append(body);
  }

  private void setRequestLineParts(ArrayList<String> requestParts) {
    String requestLine = requestParts.get(0);
    String[] requestLineParts = requestLine.split(" ");
    setRequestMethod(requestLineParts);
    setUri(requestLineParts);
    setHttpVersion(requestLineParts);
  }

  private void setRequestMethod(String[] requestLineComponents) {
    String requestMethod = requestLineComponents[0];
    try {
      this.requestMethod = RequestMethod.valueOf(requestMethod);
    } catch (IllegalArgumentException e) {
      this.requestMethod = RequestMethod.UNSUPPORTED;
    }
  }

  private void setUri(String[] requestLineComponents) {
   this.uri = requestLineComponents[1];
  }

  private void setHttpVersion(String[] requestLineComponents) {
    this.httpVersion = requestLineComponents[2];
  }

  private void setBody(ArrayList<String> requestParts) {
    Integer lastIndex = lastIndexInArray(requestParts);
    if (bodyExistInRequest(requestParts)) {
      String bodyContents = requestParts.get(lastIndex).replace("Body: ", "");
      this.body = bodyContents;
    }
  }

  private void setHeaders(ArrayList<String> requestParts) {
    boolean bodyExists = bodyExistInRequest(requestParts);
    if (bodyExists) {
      List<String> allHeaders = requestParts.subList(1, requestParts.size() - 1);
      this.headers = joinHeaders(Constants.CLRF, allHeaders);
    } else {
      List<String> allHeaders = requestParts.subList(1, requestParts.size());
      this.headers = joinHeaders(Constants.CLRF, allHeaders);
    }
  }

  private String joinHeaders(String delimeter, List<String> allHeaders){
    return String.join(delimeter, allHeaders);
  }

  private Integer lastIndexInArray(ArrayList<String> arrayList) {
    return arrayList.size() - 1;
  }

  private boolean bodyExistInRequest(ArrayList<String> requestParts){
    Integer lastIndex = lastIndexInArray(requestParts);
    String lastElement = requestParts.get(lastIndex);
    return lastElement.contains("Body: ");
  }
}
