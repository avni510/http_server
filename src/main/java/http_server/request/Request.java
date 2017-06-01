package http_server.request;

import http_server.Header;

import java.util.HashMap;
import java.util.Map;

public class Request {
  private Enum<RequestMethod> requestMethod;
  private String uri;
  private String httpVersion;
  private String header = null;
  private String body = null;
  private Header headerClass = new Header();

  public Request(Enum<RequestMethod> requestMethod, String uri, String httpVersion,
                 String header, String body) {
    this.requestMethod = requestMethod;
    this.uri = uri;
    this.httpVersion = httpVersion;
    this.header = header;
    this.body = body;
    populateHeaders();
  }

  public void populateHeaders(){
    if (header != null) {
      headerClass.populate(header);
    }
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

  public Map<String, String> getHeader() {
    return headerClass.getAllHeaders();
  }

  public String getHeaderValue(String key){
    return headerClass.getValue(key);
  }

  public String getEntireBody() {
    return this.body;
  }

  public String getBodyParam(String key){
    Map<String, String> bodyComponents = transformBody(body);
    if (bodyComponents != null) {
      return bodyComponents.get(key);
    } else {
      return null;
    }
  }

  public String getIdInUri() {
    String[] uriParts = uri.split("/");
    for (String uriComponent : uriParts) {
      if (isValueInt(uriComponent)) {
        return uriComponent;
      }
    }
    return null;
  }

  private boolean isValueInt(String value) {
    try {
      Integer.parseInt(value);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  private Map<String, String> transformBody(String body){
    Map<String, String> bodyRepresentation = new HashMap();
    if (body != null) {
      String[] bodyParts = body.split("=");
      for (int i = 0; i < (bodyParts.length - 1); i++) {
        bodyRepresentation.put(bodyParts[i], bodyParts[i + 1]);
      }
      return bodyRepresentation;
    }
    return null;
  }
}
