package http_server;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestParser {
  private BufferedReader inputStream;

  public RequestParser(BufferedReader inputStream) {
    this.inputStream = inputStream;
  }

  public Request parse() throws IOException {
    String http_request = buildHttpRequest(inputStream);
    ArrayList<String> requestComponents = new ArrayList<> (Arrays.asList(http_request.split("\r\n")));
    String[] requestLineParts = getRequestLineParts(requestComponents);
    RequestMethod requestMethod = getRequestMethod(requestLineParts);
    String uri = getUri(requestLineParts);
    String httpVersion = getHttpVersion(requestLineParts);
    ArrayList<String> headers = getHeaders(requestComponents);
    String body = getBody(requestComponents);
    Request request = buildRequest(requestMethod, uri, httpVersion, headers, body);
    return request;
  }

  private Request buildRequest(RequestMethod requestMethod, String uri, String httpVersion,
                                  ArrayList<String> headers, String body){
    Request requestNew = new RequestBuilder()
        .setRequestMethod(requestMethod)
        .setUri(uri)
        .setHttpVersion(httpVersion)
        .setHeader(headers)
        .setBody(body)
        .build();

    return requestNew;
  }

  private String buildHttpRequest(BufferedReader in) throws IOException {
    StringBuilder http_request = new StringBuilder();
    String line;
    while (null != (line = in.readLine()) && !line.isEmpty()) {
      http_request.append(line + "\r\n");
    }
    return http_request.toString();
  }

  private String[] getRequestLineParts(ArrayList<String> requestParts) {
    String requestLine = requestParts.get(0);
    return requestLine.split(" ");
  }

  private RequestMethod getRequestMethod(String[] requestLineComponents) {
    return RequestMethod.valueOf(requestLineComponents[0]);
  }

  private String getUri(String[] requestLineComponents) {
    return requestLineComponents[1];
  }

  private String getHttpVersion(String[] requestLineComponents) {
    return requestLineComponents[2];
  }

  private String getBody(ArrayList<String> requestParts) {
    return requestParts.get(requestParts.size() - 1);
  }

  private ArrayList<String> getHeaders(ArrayList<String> requestParts) {
    List<String> allHeaders = requestParts.subList(1, requestParts.size() - 1);
    return new ArrayList(allHeaders);
  }
}
