package http_server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface Handler {
  public String generate(Request request) throws IOException;
}
