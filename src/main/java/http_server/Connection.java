package http_server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Connection {
  InputStream in() throws IOException;
  OutputStream out() throws IOException;
}

