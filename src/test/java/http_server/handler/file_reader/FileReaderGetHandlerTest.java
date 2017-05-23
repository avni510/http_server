package http_server.handler.file_reader;

import http_server.response.Response;
import http_server.request.Request;
import http_server.request.RequestBuilder;
import http_server.request.RequestMethod;
import http_server.FileHelper;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FileReaderGetHandlerTest {

  @Test
  public void fileContentsAreDisplayed() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/result.txt")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost\r\n")
        .build();
    String pathToFile = System.getProperty("user.dir") + "/code/result.txt";
    FileReaderGetHandler fileReaderGetHandler = new FileReaderGetHandler(pathToFile, new FileHelper());

    Response actualResponse = fileReaderGetHandler.generate(request);

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
    FileReaderGetHandler fileReaderGetHandler = new FileReaderGetHandler(pathToFile, new FileHelper());

    Response actualResponse = fileReaderGetHandler.generate(request);

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
    FileReaderGetHandler fileReaderGetHandler = new FileReaderGetHandler(pathToFile, new FileHelper());

    Response actualResponse = fileReaderGetHandler.generate(request);

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
    FileReaderGetHandler fileReaderGetHandler = new FileReaderGetHandler(pathToFile, new FileHelper());

    Response actualResponse = fileReaderGetHandler.generate(request);

    assertEquals("206 Partial Content", actualResponse.getStatusCodeMessage());
    assertEquals("Content-Range: bytes 5-22\r\n" + "Content-Type: text/plain\r\n", actualResponse.getHeaders());
    assertEquals("e TimeLogger\nend\n", new String(actualResponse.getBody()));
  }
}