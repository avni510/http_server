package http_server;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestParser {
  private BufferedReader inputStream;
  private RequestMethod requestMethod;
  private String uri;
  private String httpVersion;
  private ArrayList<String> headers;
  private String body = null;

  public RequestParser(BufferedReader inputStream) throws FileNotFoundException {
    this.inputStream = inputStream;
  }

  public Request parse() throws Exception {
    String httpRequest = bufferedReaderToString(inputStream);
    ArrayList<String> requestComponents = new ArrayList<> (Arrays.asList(httpRequest.split("\r\n")));
    setRequestLineParts(requestComponents);
    setHeaders(requestComponents);
    setBody(requestComponents);
    Request request = buildRequest(requestMethod, uri, httpVersion, headers, body);
    return request;
  }

  private Request buildRequest(RequestMethod requestMethod, String uri, String httpVersion,
                               ArrayList<String> headers, String body){
    Request requestNew = new RequestBuilder()
        .setRequestMethod(requestMethod)
        .setUri(uri)
        .setHttpVersion(httpVersion)
        .setHeader(headers)
        .setBody(body)
        .build();
    return requestNew;
  }

  private String bufferedReaderToString(BufferedReader bufferedReader) throws Exception {
      Integer contentLength = 0;
      StringBuilder httpRequest = new StringBuilder();
      String line;
      while (null != (line = bufferedReader.readLine()) && !line.isEmpty()) {
        httpRequest.append(line + "\r\n");
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
    this.requestMethod = RequestMethod.valueOf(requestLineComponents[0]);
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
      this.headers = new ArrayList(allHeaders);
    } else {
      List<String> allHeaders = requestParts.subList(1, requestParts.size());
      this.headers = new ArrayList(allHeaders);
    }
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
