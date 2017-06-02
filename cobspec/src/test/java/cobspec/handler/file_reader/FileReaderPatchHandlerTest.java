package cobspec.handler.file_reader;

import core.Constants;

import core.response.Response;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import cobspec.FileHelper;

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
        .setHeader("Host: localhost" + Constants.CLRF)
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
        .setHeader("Host: localhost" + Constants.CLRF +
                   "If-Match: cc640aa14e96c7e21003963620c42259125749d9" + Constants.CLRF)
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
        .setHeader("Host: localhost" + Constants.CLRF +
                   "If-Match: cc640aa14e96c7e21003963620c42259125749d9" + Constants.CLRF)
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