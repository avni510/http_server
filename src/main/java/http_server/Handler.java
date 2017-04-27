package http_server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface Handler {
  public byte[] generate() throws IOException;
}
