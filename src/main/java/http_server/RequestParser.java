package http_server;
import java.io.BufferedReader;
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
  private boolean bodyExists = false;

  public RequestParser(BufferedReader inputStream) {
    this.inputStream = inputStream;
  }

  public Request parse() throws Exception {
    String http_request = buildHttpRequest(inputStream);
    ArrayList<String> requestComponents = new ArrayList<> (Arrays.asList(http_request.split("\r\n")));
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

  private String buildHttpRequest(BufferedReader in) throws Exception {
    StringBuilder http_request = new StringBuilder();
    String line;
    while (null != (line = in.readLine()) && !line.isEmpty()) {
      http_request.append(line + "\r\n");
    }
    String rawRequest = http_request.toString();
    if (rawRequest.contains("Content-Type: ")) {
     this.bodyExists = true;
    }
    if (!rawRequest.contains("Host: ")) {
      throw new Exception();
    }
    return http_request.toString();
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
    if (bodyExists) {
      this.body = requestParts.get(requestParts.size() - 1);
    }
  }

  private void setHeaders(ArrayList<String> requestParts) {
    if (bodyExists) {
      List<String> allHeaders = requestParts.subList(1, requestParts.size() - 1);
      this.headers = new ArrayList(allHeaders);
    } else {
      List<String> allHeaders = requestParts.subList(1, requestParts.size());
      this.headers = new ArrayList(allHeaders);
    }
  }
}
