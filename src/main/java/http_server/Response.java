package http_server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Response {
  private String CLRF = "\r\n";
  private String httpVersion;
  private String statusCodeMessage;
  private Map<String, String> headers;
  private String body;

  public Response(String httpVersion, String statusCodeMessage, Map<String, String> headers, String body) {
    this.httpVersion = httpVersion;
    this.statusCodeMessage = statusCodeMessage;
    this.headers = headers;
    this.body = body;
  }

  public String getHttpResponse() {
    if (headers == null && body == null) {
      return httpVersion + " " + statusCodeMessage + CLRF + CLRF;
    }
    String headerMessage = retrieveHeader(headers);
    return httpVersion + " " + statusCodeMessage + CLRF + headerMessage + CLRF + body;
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

