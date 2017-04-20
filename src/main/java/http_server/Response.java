package http_server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface Response {
  public byte[] generate() throws IOException;
}
