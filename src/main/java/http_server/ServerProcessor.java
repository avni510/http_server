package http_server;

import http_server.response.Response;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import http_server.handler.ErrorHandler;

import http_server.request.Request;
import http_server.request.RequestParser;

public class ServerProcessor implements Runnable {
  private Connection clientConnection;
  private Middleware app;

  public ServerProcessor(Connection clientConnection, Middleware app){
    this.clientConnection = clientConnection;
    this.app = app;
  }

  public void run() {
    try {
      BufferedReader optimizedInputStream = read(clientConnection);
      Response httpResponse = getHttpResponse(optimizedInputStream);
      write(clientConnection, httpResponse);
      clientConnection.out().close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private BufferedReader read(Connection clientConnection) throws IOException {
   return new BufferedReader(new InputStreamReader(clientConnection.in()));
  }

  private Response getHttpResponse(BufferedReader inputStream) throws Exception {
    RequestParser requestParser = new RequestParser(inputStream);
    Request request = null;
    try {
      request = requestParser.parse();
    } catch (Exception e) {
      ErrorHandler errorHandler = new ErrorHandler(400);
      return errorHandler.generate(request);
    }
    return app.call(request);
  }

  private void write(Connection clientConnection, Response response) throws Exception{
    clientConnection.out().write(response.getHttpResponseBytes());
  }
}
