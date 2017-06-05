package core.response;

import core.request.Header;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
  private String httpVersion;
  static Map<Integer, String> allStatusCodes = new HashMap<>();
  static {
    allStatusCodes.put(200, "200 OK");
    allStatusCodes.put(404, "404 Not Found");
    allStatusCodes.put(301, "301 Moved Permanently");
    allStatusCodes.put(302, "302 Found");
    allStatusCodes.put(500, "500 Server Error");
    allStatusCodes.put(400, "400 Bad Request");
    allStatusCodes.put(405, "405 Method Not Allowed");
    allStatusCodes.put(418, "418 I'm a teapot");
    allStatusCodes.put(401, "401 Unauthorized");
    allStatusCodes.put(206, "206 Partial Content");
    allStatusCodes.put(204, "204 No Content");
  }
  private String statusCodeMessage;
  private byte[] body = null;
  private Header header = new Header();

  public ResponseBuilder setHttpVersion(String httpVersion) {
    this.httpVersion = httpVersion;
    return this;
  }

  public ResponseBuilder setStatusCode(Integer statusCode) {
    this.statusCodeMessage = ResponseBuilder.allStatusCodes.get(statusCode);
    return this;
  }

  public ResponseBuilder setHeader(String key, String value) {
    header.add(key, value);
    return this;
  }

  public ResponseBuilder setBody(String body) {
    this.body = transformIntoBytes(body);
    return this;
  }

  public ResponseBuilder setBody(byte[] body) {
    this.body = body;
    return this;
  }

  public Response build() {
    return new Response(httpVersion, statusCodeMessage, header, body);
  }

  private byte[] transformIntoBytes(String body){
    return body.getBytes();
  }
}


