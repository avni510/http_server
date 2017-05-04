package http_server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Request {
  private Enum<RequestMethod> requestMethod;
  private String uri;
  private String httpVersion;
  private ArrayList<String> header = null;
  private Map<String, String> body = null;

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
    this.body = transformBody(body);
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

  public Map<String, String> getBody() {
    return body;
  }

  public String getBodyParam(String key){
    if (body != null) {
      return body.get(key);
    } else {
      return null;
    }
  }

  private Map<String, String> transformBody(String body){
    if (body != null) {
      String[] bodyParts = body.split("=");
      Map<String, String> bodyRepresentation = new HashMap();
      for (int i = 0; i < (bodyParts.length - 1); i++) {
        bodyRepresentation.put(bodyParts[i], bodyParts[i + 1]);
      }
      return bodyRepresentation;
    }
    return null;
  }
}
