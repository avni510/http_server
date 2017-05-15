package http_server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class ServerProcessor implements Processor {

  public void execute(Connection clientConnection) throws Exception {
    BufferedReader optimizedInputStream = read(clientConnection);
    Response httpResponse = Router.generateHttpResponse(optimizedInputStream);
    write(clientConnection, httpResponse);
    clientConnection.out().close();
  }

  private BufferedReader read(Connection clientConnection) throws IOException {
   return new BufferedReader(new InputStreamReader(clientConnection.in()));
  }

  private void write(Connection clientConnection, Response response) throws Exception{
    clientConnection.out().write(response.getHttpResponseBytes());
  }
}
