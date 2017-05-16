package http_server;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class HelloWorldHandler implements Handler{

  public Response generate(Request request) throws UnsupportedEncodingException {
    Response response = new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeader("Content-Type", "text/plain")
        .setBody("hello world")
        .build();
    return response;
  }
}
