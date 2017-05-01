package http_server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class ServerProcessor implements Processor {

  public void execute(Connection clientConnection) throws Exception {
    String rootPathDirectory = System.getProperty("user.dir") + "/code";
    BufferedReader optimizedInputStream = read(clientConnection);
    Router router = new Router();
    router.populateRoutes(rootPathDirectory);
    String httpResponse = router.generateHttpResponse(optimizedInputStream);
    write(clientConnection, httpResponse);
    clientConnection.out().close();
  }

  private BufferedReader read(Connection clientConnection) throws IOException {
   return new BufferedReader(new InputStreamReader(clientConnection.in()));
  }

  private void write(Connection clientConnection, String response) throws Exception{
    clientConnection.out().write(response.getBytes());
  }
}
