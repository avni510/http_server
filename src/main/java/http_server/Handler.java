package http_server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface Handler {
  Response generate(Request request) throws IOException;
}
