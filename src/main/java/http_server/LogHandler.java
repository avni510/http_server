package http_server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LogHandler implements Handler{

  public String generate(Request request) throws IOException {
    Map<String, String> header = new HashMap();
    header.put("Content-Type", "text/plain");
    Response response = new ResponseBuilder()
                        .setHttpVersion("HTTP/1.1")
                        .setStatusCode(200)
                        .setHeaders(header)
                        .setBody(getBody(request))
                        .build();
    return response.getHttpResponse();
  }

  private String getBody(Request request){
    return request.getRequestMethod().toString() + " " +
           request.getUri() + " " + request.getHttpVersion();
  }
}
