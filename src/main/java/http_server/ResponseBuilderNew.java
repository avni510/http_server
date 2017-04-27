package http_server;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilderNew {
  private String httpVersion;
  private Map<Integer, String> allStatusCodes;
  private String statusCodeMessage;
  private Map<String, String> headers = null;
  private String body = null;

  public ResponseBuilderNew() {
    this.allStatusCodes = new HashMap();
    allStatusCodes.put(200, "200 OK");
    allStatusCodes.put(404, "404 Not Found");
    allStatusCodes.put(301, "301 Moved Permanently");
    allStatusCodes.put(302, "302 Moved Temporarily");
    allStatusCodes.put(500, "500 Server Error");
  }

  public ResponseBuilderNew setHttpVersion(String httpVersion) {
    this.httpVersion = httpVersion;
    return this;
  }

  public ResponseBuilderNew setStatusCode(Integer statusCode) {
    this.statusCodeMessage = allStatusCodes.get(statusCode);
    return this;
  }

  public ResponseBuilderNew setHeaders(Map<String, String> headers) {
    this.headers = headers;
    return this;
  }

  public ResponseBuilderNew setbody(String body) {
    this.body = body;
    return this;
  }

  public Response build() {
    return new Response(httpVersion, statusCodeMessage, headers, body);
  }
}


