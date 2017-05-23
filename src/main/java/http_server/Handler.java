package http_server;

import java.io.IOException;

public interface Handler {
  Response generate(Request request) throws IOException;
}
