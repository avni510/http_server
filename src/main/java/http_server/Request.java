package http_server;

import java.util.HashMap;
import java.util.Map;

public class Request {
  private String requestString;
  private Map<String, Map<String, String>> requestComponents = new HashMap<String, Map<String, String>>();

  public Request(String requestString) {
    this.requestString = requestString;
    parseRequest();
  }

  public String getRequestMethod() {
    return requestComponents.get("request").get("request_method");
  }

  public String getUri() {
    return requestComponents.get("request").get("uri");
  }

  public String getHttpVersion() {
    return requestComponents.get("request").get("http_version");
  }

  public String getHostName() {
    return requestComponents.get("header").get("host");
  }

  private void parseRequest() {
    String[] messageComponents = requestString.split("\r\n");
    requestComponents.put("request", parseRequestLine(messageComponents[0]));
    requestComponents.put("header", parseHeaderLine(messageComponents[1]));
  }

  private Map parseRequestLine(String requestLine) {
    Map<String, String> requestLineComponents = new HashMap<>();
    String[] requestMessageComponents = requestLine.split(" ");
    requestLineComponents.put("request_method", requestMessageComponents[0]);
    requestLineComponents.put("uri", requestMessageComponents[1]);
    requestLineComponents.put("http_version", requestMessageComponents[2]);
    return requestLineComponents;
  }

  private Map parseHeaderLine(String headerLine) {
    Map<String, String> headerLineComponents = new HashMap<>();
    String[] headerMessageComponents = headerLine.split(" ");
    headerLineComponents.put("host", headerMessageComponents[1]);
    return headerLineComponents;
  }
}
