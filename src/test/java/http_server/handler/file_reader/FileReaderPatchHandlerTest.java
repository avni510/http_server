package http_server.handler.file_reader;

import http_server.Response;
import http_server.Request;
import http_server.RequestBuilder;
import http_server.RequestMethod;
import http_server.FileHelper;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class FileReaderPatchHandlerTest {

  private void writeToFile(String content){
    String pathToFile = System.getProperty("user.dir") + "/code/result.txt";
    try {
      File file = new File(pathToFile);
      PrintWriter writer = new PrintWriter(file, String.valueOf(StandardCharsets.UTF_8));
      writer.write(content);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Response resetFileContents(String pathToFile) throws IOException {
    Request getRequest = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/result.txt")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost\r\n")
        .build();

    FileReaderGetHandler fileReaderGetHandler = new FileReaderGetHandler(pathToFile, new FileHelper());
    return fileReaderGetHandler.generate(getRequest);
  }

  @Test
  public void patchRequestIsHandled() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.PATCH)
        .setUri("/result.txt")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost\r\nIf-Match: cc640aa14e96c7e21003963620c42259125749d9\r\n")
        .setBody("data=foobar")
        .build();
    String pathToFile = System.getProperty("user.dir") + "/code/result.txt";
    FileReaderPatchHandler fileReaderPatchHandler = new FileReaderPatchHandler(pathToFile, new FileHelper());

    Response actualResponse = fileReaderPatchHandler.generate(request);

    assertEquals("204 No Content", actualResponse.getStatusCodeMessage());
    writeToFile("module TimeLogger\nend\n");
  }

  @Test
  public void fileIsUpdatedAfterPatchRequest() throws IOException {
    Request patchRequest = new RequestBuilder()
        .setRequestMethod(RequestMethod.PATCH)
        .setUri("/result.txt")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost\r\nIf-Match: cc640aa14e96c7e21003963620c42259125749d9\r\n")
        .setBody("data=foobar")
        .build();
    String pathToFile = System.getProperty("user.dir") + "/code/result.txt";
    FileReaderPatchHandler fileReaderPatchHandler = new FileReaderPatchHandler(pathToFile, new FileHelper());
    fileReaderPatchHandler.generate(patchRequest);

    Response actualResponse = resetFileContents(pathToFile);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("data=foobar", new String(actualResponse.getBody()));
    writeToFile("module TimeLogger\nend\n");
  }
}