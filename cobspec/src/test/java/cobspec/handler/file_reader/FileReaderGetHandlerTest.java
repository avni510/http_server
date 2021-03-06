package cobspec.handler.file_reader;

import core.Constants;

import core.response.Response;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import cobspec.FileHelper;

import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileReaderGetHandlerTest {

  @Test
  public void fileContentsAreDisplayed() throws IOException {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri("/result.txt")
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