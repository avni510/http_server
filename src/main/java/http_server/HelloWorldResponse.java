package http_server;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class HelloWorldResponse implements Response{

  public byte[] generate() throws UnsupportedEncodingException {
    ResponseBuilder responseBuilder = new ResponseBuilder();
    Map<String, String> header = new HashMap<>();
    header.put("Content-Type", "text/plain");
    return responseBuilder.run(200, header, "hello world");
  }
}
