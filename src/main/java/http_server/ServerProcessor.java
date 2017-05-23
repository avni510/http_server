package http_server;

import http_server.response.Response;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class ServerProcessor implements Runnable {
  private Connection clientConnection;
  private ServerResponse serverResponse;

  public ServerProcessor(Connection clientConnection, ServerResponse serverResponse){
    this.clientConnection = clientConnection;
    this.serverResponse = serverResponse;
  }

  public void run() {
    try {
      BufferedReader optimizedInputStream = read(clientConnection);
      Response httpResponse = serverResponse.getHttpResponse(optimizedInputStream);
      write(clientConnection, httpResponse);
      clientConnection.out().close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private BufferedReader read(Connection clientConnection) throws IOException {
   return new BufferedReader(new InputStreamReader(clientConnection.in()));
  }

  private void write(Connection clientConnection, Response response) throws Exception{
    clientConnection.out().write(response.getHttpResponseBytes());
  }
}
