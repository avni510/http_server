package http_server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Objects;

public class ServerProcessor implements Processor {
  private Connection clientConnection;

  public void execute(Connection clientConnection) throws Exception {
    this.clientConnection = clientConnection;
    BufferedReader in = read();
    StringBuilder http_request = buildHttpRequest(in);
    Request request = new Request(http_request.toString());
    if (Objects.equals(request.getRequestMethod(), "GET") && Objects.equals(request.getUri(), "/")){
      String response = "HTTP/1.1 200 OK\r\n\r\n" + "hello world";
      write(response);
    }
    clientConnection.out().close();
  }

  private BufferedReader read() throws IOException {
   return new BufferedReader(new InputStreamReader(clientConnection.in()));
  }

  private StringBuilder buildHttpRequest(BufferedReader in) throws IOException {
    StringBuilder http_request = new StringBuilder();
    String line;
    while ((line = in.readLine()) != null && !line.isEmpty()) {
      http_request.append(line + "\r\n");
    }
    return http_request;
  }

  private void write(String response) throws Exception{
    clientConnection.out().write(response.getBytes("UTF-8"));
  }
}
