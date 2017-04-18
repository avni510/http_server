package http_server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class ServerProcessor implements Processor {
  private Connection clientConnection;

  public void execute(Connection clientConnection) throws Exception {
    this.clientConnection = clientConnection;
    BufferedReader in = read();
    logRequest(in);
    String response = "HTTP/1.1 200 OK\r\n\r\n" + "hello world";
    write(response);
    clientConnection.out().close();
  }

  private BufferedReader read() throws IOException {
   return new BufferedReader(new InputStreamReader(clientConnection.in()));
  }

  private void logRequest(BufferedReader in) throws IOException {
    String line;
    while ((line = in.readLine()) != null && !line.isEmpty()) {
      System.out.println(line);
    }
  }

  private void write(String response) throws Exception{
    clientConnection.out().write(response.getBytes("UTF-8"));
  }
}
