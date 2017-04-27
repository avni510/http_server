package http_server;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class HelloWorldHandler implements Handler{

  public String generate() throws UnsupportedEncodingException {
    Map<String, String> header = new HashMap();
    header.put("Content-Type", "text/plain");
    Response response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeaders(header)
        .setBody("hello world")
        .build();
    return response.getHttpResponse();
  }
}
