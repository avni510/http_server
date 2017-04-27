package http_server;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class HelloWorldResponse implements Handler{

  public byte[] generate() throws UnsupportedEncodingException {
    ResponseBuilder responseBuilder = new ResponseBuilder();
    Map<String, String> header = new HashMap<>();
    header.put("Content-Type", "text/plain");
    String body = "hello world";
    return responseBuilder.run(200, header, body);
  }
}
