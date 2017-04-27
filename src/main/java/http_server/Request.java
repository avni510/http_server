package http_server;

import java.util.ArrayList;

public class Request {
  private Enum<RequestMethod> requestMethod;
  private String uri;
  private String httpVersion;
  private ArrayList<String> header = null;
  private String body = null;

  public Request(
     Enum<RequestMethod> requestMethod,
     String uri,
     String httpVersion,
     ArrayList<String> header,
     String body) {
    this.requestMethod = requestMethod;
    this.uri = uri;
    this.httpVersion = httpVersion;
    this.header = header;
    this.body = body;
  }

  public Enum<RequestMethod> getRequestMethod() {
    return requestMethod;
  }

  public String getUri() {
    return uri;
  }

  public String getHttpVersion() {
    return httpVersion;
  }

  public ArrayList<String> getHeader() {
    return header;
  }

  public String getBody() {
    return body;
  }
}
