package http_server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Connection {
  public InputStream in() throws IOException;
  public OutputStream out() throws IOException;
}
