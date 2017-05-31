package http_server.handler.file_reader;

import http_server.Constants;

import http_server.cobspec.handler.file_reader.FileReaderGetHandler;
import http_server.response.Response;

import http_server.request.Request;
import http_server.request.RequestBuilder;
import http_server.request.RequestMethod;

import http_server.cobspec.FileHelper;

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
        .setHeader("Host: localhost")
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
        .setHeader("Host: localhost" + Constants.CLRF + "Range: bytes=0-5" + Constants.CLRF)
        .build();
    String pathToFile = System.getProperty("user.dir") + "/code/result.txt";
    FileReaderGetHandler fileReaderGetHandler = new FileReaderGetHandler(pathToFile, new FileHelper());

    Response actualResponse = fileReaderGetHandler.generate(request);

    assertEquals("206 Partial Content", actualResponse.getStatusCodeMessage());
    assertEquals("Content-Range: bytes 0-6" + Constants.CLRF +
                          "Content-Type: text/plain" + Constants.CLRF, actualResponse.getHeaders());
    assertEquals("module", new String(actualResponse.getBody()));
  }

  @Test
  public void partialFileContentsAreDisplayedForRangeWithNoStart() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/result.txt")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost" + Constants.CLRF + "Range: bytes=-5" + Constants.CLRF)
        .build();
    String pathToFile = System.getProperty("user.dir") + "/code/result.txt";
    FileReaderGetHandler fileReaderGetHandler = new FileReaderGetHandler(pathToFile, new FileHelper());

    Response actualResponse = fileReaderGetHandler.generate(request);

    assertEquals("206 Partial Content", actualResponse.getStatusCodeMessage());
    assertEquals("Content-Range: bytes 17-22" + Constants.CLRF + "Content-Type: text/plain" + Constants.CLRF, actualResponse.getHeaders());
    assertEquals("\nend\n", new String(actualResponse.getBody()));
  }

  @Test
  public void partialFileContentsAreDisplayedForRangeWithNoEnd() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/result.txt")
        .setHttpVersion("HTTP/1.1")
        .setHeader("Host: localhost" + Constants.CLRF + "Range: bytes=5-" + Constants.CLRF)
        .build();
    String pathToFile = System.getProperty("user.dir") + "/code/result.txt";
    FileReaderGetHandler fileReaderGetHandler = new FileReaderGetHandler(pathToFile, new FileHelper());

    Response actualResponse = fileReaderGetHandler.generate(request);

    assertEquals("206 Partial Content", actualResponse.getStatusCodeMessage());
    assertEquals("Content-Range: bytes 5-22" + Constants.CLRF +
                          "Content-Type: text/plain" + Constants.CLRF, actualResponse.getHeaders());
    assertEquals("e TimeLogger\nend\n", new String(actualResponse.getBody()));
  }
}