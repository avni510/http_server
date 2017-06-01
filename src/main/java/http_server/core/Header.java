package http_server.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Header {
  private Map<String, String> headerStore = new HashMap<>();

  public void add(String key, String value){
    headerStore.put(key, value);
  }

  public void populate(String headerMessage) {
    ArrayList<String[]> headerMessageComponents = splitIntoHeaderParts(headerMessage);
    populateHeaders(headerMessageComponents);
  }

  public Map<String, String> getAllHeaders(){
    return headerStore;
  }

  public String convertHeadersToString(){
    StringBuilder allHeaders = new StringBuilder();
    for (Map.Entry<String, String> header : headerStore.entrySet()) {
      allHeaders.append(header.getKey() + ": " + header.getValue() + Constants.CLRF);
    }
    return allHeaders.toString();
  }

  public String getValue(String key){
    return headerStore.get(key);
  }

  private ArrayList<String[]> splitIntoHeaderParts(String headers) {
    String[] splitEachHeader = headers.split(Constants.CLRF);
    ArrayList<String[]> headerMessageComponents = new ArrayList<>();
    for(String header : splitEachHeader) {
      headerMessageComponents.add(header.split(": "));
    }
    return headerMessageComponents;
  }

  private void populateHeaders(
      ArrayList<String[]> allHeaderParts) {
    for (String[] eachHeader : allHeaderParts) {
      String headerName = getHeaderName(eachHeader);
      String headerValue = getHeaderDescription(eachHeader);
      headerStore.put(headerName, headerValue);
    }
  }

  private String getHeaderName(String[] headerParts) {
   return headerParts[0];
  }

  private String getHeaderDescription(String[] headerParts) {
    return headerParts[1];
  }

}