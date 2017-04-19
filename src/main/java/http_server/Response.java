package http_server;

import java.io.UnsupportedEncodingException;

public interface Response {
  public byte[] generate() throws UnsupportedEncodingException;
}
