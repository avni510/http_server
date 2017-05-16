package http_server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class Response {
  private String CLRF = "\r\n";
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

  public byte[] getHttpResponseBytes(){
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
      return httpVersion + " " + statusCodeMessage + CLRF + CLRF;
    }
    String headerMessage = getHeaders();
    return httpVersion + " " + statusCodeMessage + CLRF + headerMessage + CLRF;
  }

  public String getStatusCodeMessage(){
    return this.statusCodeMessage;
  }

  public String getHeaders(){
    return header.convertHeadersToString();
  }

  public byte[] getBody(){
    return this.body;
  }
}

