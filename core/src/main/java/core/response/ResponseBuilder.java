package core.response;

import core.request.Header;

public class ResponseBuilder {
  private String httpVersion;
  private String statusCodeMessage;
  private byte[] body = null;
  private Header header = new Header();

  public ResponseBuilder setHttpVersion(String httpVersion) {
    this.httpVersion = httpVersion;
    return this;
  }

  public ResponseBuilder setStatusCode(String statusCode) {
    this.statusCodeMessage = statusCode;
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


