package http_server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Header {
  private String CLRF = "\r\n";

  public Map<String,String> getAllHeaders(String headerMessage) {
    Map<String, String> allHeaderComponents = new HashMap<>();
    headerMessage = parseHeader(headerMessage);
    ArrayList<String[]> headerMessageComponents = splitIntoHeaderParts(headerMessage);
    return populateHeaders(headerMessageComponents, allHeaderComponents);
  }

  public String createHttpHeader(Map<String, String> headerComponents){
    StringBuilder allHeaders = new StringBuilder();
    for (Map.Entry<String, String> header : headerComponents.entrySet()) {
      allHeaders.append(header.getKey() + ": " + header.getValue() + CLRF);
    }
    return allHeaders.toString();
  }

  private String parseHeader(String header) {
    header = header.replace(":", "");
    return header.trim();
  }

  private ArrayList<String[]> splitIntoHeaderParts(String headers) {
    String[] splitEachHeader = headers.split("\r\n");
    ArrayList<String[]> headerMessageComponents = new ArrayList<>();
    for(String header : splitEachHeader) {
      headerMessageComponents.add(header.split(" "));
    }
    return headerMessageComponents;
  }

  private Map<String, String> populateHeaders(
      ArrayList<String[]> allHeaderParts,
      Map<String, String> allHeaderComponents) {
    for(String[] eachHeader : allHeaderParts) {
      String headerName = getHeaderName(eachHeader);
      String headerValue = getHeaderValue(eachHeader);
      allHeaderComponents.put(headerName, headerValue);
    }
    return allHeaderComponents;
  }

  private String getHeaderName(String[] headerParts) {
   return headerParts[0];
  }

  private String getHeaderValue(String[] headerParts) {
    return headerParts[1];
  }


}