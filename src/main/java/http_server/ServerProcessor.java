package http_server;

import java.io.FileReader;
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
      HelloWorldResponse helloWorldResponse = new HelloWorldResponse();
      byte [] response = helloWorldResponse.generate();
      write(response);
    } else if (Objects.equals(request.getRequestMethod(), "GET") && Objects.equals(request.getUri(), "/code")) {
      DirectoryResponse directoryResponse = new DirectoryResponse("/Users/avnikothari/Desktop/resident_apprenticeship/java/http_server/code");
      byte [] response = directoryResponse.generate();
      write(response);
    }
//    else if (Objects.equals(request.getRequestMethod(), "GET") && Objects.equals(request.getUri(), "/code/result.txt")) {
//      FileManager fileManager = new FileManager();
//      String filePath = fileManager.getAbsolutePath(request.getUri(),"/Users/avnikothari/Desktop/resident_apprenticeship/java/http_server/code");
//      FileReaderResponse fileReaderResponse = new FileReaderResponse(filePath);
//      byte[] response = fileReaderResponse.generate();
//      write(response);
//    }

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

  private void write(byte[] response) throws Exception{
    clientConnection.out().write(response);
  }
}
