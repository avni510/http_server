package http_server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Response {
  private String CLRF = "\r\n";
  private String httpVersion;
  private String statusCodeMessage;
  private Map<String, String> headers;
  private byte[] body;

  public Response(String httpVersion, String statusCodeMessage, Map<String, String> headers, byte[] body) {
    this.httpVersion = httpVersion;
    this.statusCodeMessage = statusCodeMessage;
    this.headers = headers;
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
    if (headers == null) {
      return httpVersion + " " + statusCodeMessage + CLRF + CLRF;
    }
    String headerMessage = retrieveHeader(headers);
    return httpVersion + " " + statusCodeMessage + CLRF + headerMessage + CLRF;
  }

  public String getStatusCodeMessage(){
    return this.statusCodeMessage;
  }

  public String getHeaders(){
    return retrieveHeader(headers);
  }

  public byte[] getBody(){
    return this.body;
  }

  public String retrieveHeader(Map<String, String> header) {
    List<String> allHeaders = new ArrayList();
    for(Map.Entry<String, String> entry: header.entrySet()){
      allHeaders.add(entry.getKey() + ": " + entry.getValue());
    }
    return mergeHeaders(allHeaders);
  }

  public String mergeHeaders(List<String> headers) {
    StringBuilder mergedHeader = new StringBuilder();
    for(String header : headers) {
      mergedHeader.append(header + CLRF);
    }
    return mergedHeader.toString();
  }
}

