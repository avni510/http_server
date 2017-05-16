package http_server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class ServerProcessor implements Processor {
  private Connection clientConnection;

  public ServerProcessor(Connection clientConnection){
    this.clientConnection = clientConnection;
  }

  public void run() {
    try {
      BufferedReader optimizedInputStream = read(clientConnection);
      Response httpResponse = Router.generateHttpResponse(optimizedInputStream);
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
