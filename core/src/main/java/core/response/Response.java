package core.response;

import core.Constants;

import core.request.Header;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Response {
  private String httpVersion;
  private String statusCodeMessage;
  private Header header;
  private byte[] body;

  public Response(String httpVersion, String statusCodeMessage, Header header, byte[] body) {
    this.httpVersion = httpVersion;
    this.statusCodeMessage = statusCodeMessage;
    this.header = header;
    this.body = body;
  }

  public byte[] getHttpResponseBytes() {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] responseLineAndHeader = getResponseLineAndHeader().getBytes();
    try {
      byteArrayOutputStream.write(responseLineAndHeader);
      if (this.body != null) {
        byteArrayOutputStream.write(this.body);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return byteArrayOutputStream.toByteArray();
  }

  public String getResponseLineAndHeader() {
    if (header == null) {
      return httpVersion + " " + statusCodeMessage + Constants.CLRF + Constants.CLRF;
    }
    String headerMessage = getHeaders();
    return httpVersion + " " + statusCodeMessage + Constants.CLRF + headerMessage + Constants.CLRF;
  }

  public String getStatusCodeMessage() {
    return this.statusCodeMessage;
  }

  public String getHeaders() {
    return header.convertHeadersToString();
  }

  public byte[] getBody() {
    return this.body;
  }
}

