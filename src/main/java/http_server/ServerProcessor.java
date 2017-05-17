package http_server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class ServerProcessor implements Processor {
  private Connection clientConnection;
  private Router router;

  public ServerProcessor(Connection clientConnection, Router router){
    this.clientConnection = clientConnection;
    this.router = router;
  }

  public void run() {
    try {
      BufferedReader optimizedInputStream = read(clientConnection);
      Response httpResponse = router.generateHttpResponse(optimizedInputStream);
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
