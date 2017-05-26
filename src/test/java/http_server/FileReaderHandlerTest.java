package http_server;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class FileReaderHandlerTest {

  @Test
  public void fileContentsAreDisplayed() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/result.txt")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost\r\n")
        .build();
    String pathToFile = System.getProperty("user.dir") + "/code/result.txt";
    FileReaderHandler fileReaderHandler = new FileReaderHandler(pathToFile, new FileHelper());

    Response actualResponse = fileReaderHandler.generate(request);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("module TimeLogger\nend\n", new String(actualResponse.getBody()));
  }

  @Test
  public void partialFileContentsAreDisplayedForSpecifiedRangeValues() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/result.txt")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost\r\nRange: bytes=0-5\r\n")
        .build();
    String pathToFile = System.getProperty("user.dir") + "/code/result.txt";
    FileReaderHandler fileReaderHandler = new FileReaderHandler(pathToFile, new FileHelper());

    Response actualResponse = fileReaderHandler.generate(request);

    assertEquals("206 Partial Content", actualResponse.getStatusCodeMessage());
    assertEquals("Content-Range: bytes 0-6\r\n" + "Content-Type: text/plain\r\n", actualResponse.getHeaders());
    assertEquals("module", new String(actualResponse.getBody()));
  }

  @Test
  public void partialFileContentsAreDisplayedForRangeWithNoStart() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/result.txt")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost\r\nRange: bytes=-5\r\n")
        .build();
    String pathToFile = System.getProperty("user.dir") + "/code/result.txt";
    FileReaderHandler fileReaderHandler = new FileReaderHandler(pathToFile, new FileHelper());

    Response actualResponse = fileReaderHandler.generate(request);

    assertEquals("206 Partial Content", actualResponse.getStatusCodeMessage());
    assertEquals("Content-Range: bytes 17-22\r\n" + "Content-Type: text/plain\r\n", actualResponse.getHeaders());
    assertEquals("\nend\n", new String(actualResponse.getBody()));
  }

  @Test
  public void partialFileContentsAreDisplayedForRangeWithNoEnd() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/result.txt")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost\r\nRange: bytes=5-\r\n")
        .build();
    String pathToFile = System.getProperty("user.dir") + "/code/result.txt";
    FileReaderHandler fileReaderHandler = new FileReaderHandler(pathToFile, new FileHelper());

    Response actualResponse = fileReaderHandler.generate(request);

    assertEquals("206 Partial Content", actualResponse.getStatusCodeMessage());
    assertEquals("Content-Range: bytes 5-22\r\n" + "Content-Type: text/plain\r\n", actualResponse.getHeaders());
    assertEquals("e TimeLogger\nend\n", new String(actualResponse.getBody()));
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
    FileReaderHandler fileReaderHandler = new FileReaderHandler(pathToFile, new FileHelper());

    Response actualResponse = fileReaderHandler.generate(request);

    assertEquals("204 No Content", actualResponse.getStatusCodeMessage());
    writeToFile("module TimeLogger\nend\n");
  }

  private void writeToFile(String content){
    String pathToFile = System.getProperty("user.dir") + "/code/result.txt";
    try {
      File file = new File(pathToFile);
      PrintWriter writer = new PrintWriter(file, "UTF-8");
      writer.write(content);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
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
    FileReaderHandler fileReaderHandler = new FileReaderHandler(pathToFile, new FileHelper());
    fileReaderHandler.generate(patchRequest);
    Request getRequest = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/result.txt")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost\r\n")
        .build();

    Response actualResponse = fileReaderHandler.generate(getRequest);

    assertEquals("200 OK", actualResponse.getStatusCodeMessage());
    assertEquals("data=foobar", new String(actualResponse.getBody()));
    writeToFile("module TimeLogger\nend\n");
  }
}