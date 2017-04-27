package http_server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Objects;

public class ServerProcessor implements Processor {
  private Connection clientConnection;
  private String rootPathDirectory;

  public void execute(Connection clientConnection) throws Exception {
    this.clientConnection = clientConnection;
    this.rootPathDirectory = System.getProperty("user.dir") + "/code";
    BufferedReader optimizedInputStream = read();
    RequestParser requestParser = new RequestParser(optimizedInputStream);
    Request request = requestParser.parse();
    if (Objects.equals(request.getRequestMethod(), RequestMethod.GET) && Objects.equals(request.getUri(), "/")){
      HelloWorldHandler helloWorldHandler = new HelloWorldHandler();
      String response = helloWorldHandler.generate();
      write(response);
    } else if (Objects.equals(request.getRequestMethod(), RequestMethod.GET) && Objects.equals(request.getUri(), "/code")) {
      DirectoryHandler directoryHandler = new DirectoryHandler(rootPathDirectory);
      String response = directoryHandler.generate();
      write(response);
    } else { loadFileContents(request, rootPathDirectory); }

    clientConnection.out().close();
  }

  private BufferedReader read() throws IOException {
   return new BufferedReader(new InputStreamReader(clientConnection.in()));
  }

  private void loadFileContents(Request request, String rootPathDirectory) throws Exception {
    FileManager fileManager = new FileManager();
    FileValidation fileValidation = new FileValidation(fileManager);

    if (Objects.equals(request.getRequestMethod(), RequestMethod.GET) && fileValidation.hasRelativePath(rootPathDirectory, request.getUri())) {
      String filePath = fileManager.getAbsolutePath(request.getUri(), rootPathDirectory);
      FileReaderHandler fileReaderHandler = new FileReaderHandler(filePath);
      String response = fileReaderHandler.generate();
      write(response);
    } else if(Objects.equals(request.getRequestMethod(), RequestMethod.GET) && !fileValidation.hasRelativePath(rootPathDirectory, request.getUri())) {
      Response response = new ResponseBuilder()
          .setHttpVersion("HTTP/1.1")
          .setStatusCode(404)
          .build();
      String httpResponse = response.getHttpResponse();
      write(httpResponse);
    }
  }

  private void write(String response) throws Exception{
    clientConnection.out().write(response.getBytes());
  }
}
