package http_server;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseBuilder {
  private String httpVersion = "HTTP/1.1";
  private String CLRF = "\r\n";
  private String characterSet = "UTF-8";

  public byte[] run (Integer statusCode, Map<String, String> header, String body) throws UnsupportedEncodingException {
    String statusCodeMessage = retrieveStatusCode(statusCode);
    String headerMessage = retrieveHeader(header);
    String response = httpVersion + " " + statusCodeMessage + CLRF + headerMessage + CLRF + body;
    return response.getBytes(characterSet);
  }

  public byte[] run(Integer statusCode) throws UnsupportedEncodingException {
    String statusCodeMessage = retrieveStatusCode(statusCode);
    String response = httpVersion + " " + statusCodeMessage + CLRF + CLRF;
    return response.getBytes(characterSet);
  }


  public String retrieveStatusCode(Integer statusCode) {
    Map<Integer, String> allStatusCodes = new HashMap<>();
    allStatusCodes.put(200, "200 OK");
    allStatusCodes.put(404, "404 Not Found");
    allStatusCodes.put(301, "301 Moved Permanently");
    allStatusCodes.put(302, "302 Moved Temporarily");
    allStatusCodes.put(500, "500 Server Error");
    return allStatusCodes.get(statusCode);
  }

  public String retrieveHeader(Map<String, String> header) {
    List<String> allHeaders = new ArrayList<>();
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
