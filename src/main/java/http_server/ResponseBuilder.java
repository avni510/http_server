package http_server;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
  private String httpVersion;
  private Map<Integer, String> allStatusCodes;
  private String statusCodeMessage;
  private Map<String, String> headers = null;
  private String body = null;

  public ResponseBuilder() {
    this.allStatusCodes = new HashMap();
    allStatusCodes.put(200, "200 OK");
    allStatusCodes.put(404, "404 Not Found");
    allStatusCodes.put(301, "301 Moved Permanently");
    allStatusCodes.put(302, "302 Moved Temporarily");
    allStatusCodes.put(500, "500 Server Error");
  }

  public ResponseBuilder setHttpVersion(String httpVersion) {
    this.httpVersion = httpVersion;
    return this;
  }

  public ResponseBuilder setStatusCode(Integer statusCode) {
    this.statusCodeMessage = allStatusCodes.get(statusCode);
    return this;
  }

  public ResponseBuilder setHeaders(Map<String, String> headers) {
    this.headers = headers;
    return this;
  }

  public ResponseBuilder setBody(String body) {
    this.body = body;
    return this;
  }

  public Response build() {
    return new Response(httpVersion, statusCodeMessage, headers, body);
  }
}


