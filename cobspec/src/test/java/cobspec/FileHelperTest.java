package cobspec;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileHelperTest {
  private String rootFilePath;

  @Before
  public void setup() {
    rootFilePath = System.getProperty("user.dir") + "/code";
  }

  private void addNameAndRelativePath(Map<String, String> paths, String fileName ) {
    paths.put(fileName, "/" + fileName);
  }

  private Map<String, String> populateNamesAndRelativePaths(){
    Map<String, String> paths = new HashMap();
    addNameAndRelativePath(paths, "log_time_entry.txt");
    addNameAndRelativePath(paths, "result.txt");
    addNameAndRelativePath(paths, "validation.txt");
    addNameAndRelativePath(paths, "image.png");
    return paths;
  }

  private ArrayList<String> populateRelativePaths(){
    ArrayList<String> relativePaths = new ArrayList<>();
    relativePaths.add("/log_time_entry.txt");
    relativePaths.add("/result.txt");
    relativePaths.add("/validation.txt");
    relativePaths.add("/image.png");
    return relativePaths;
  }

  @Test
  public void fileNamesAndRelativePathsAreReturned() {
    FileHelper fileHelper = new FileHelper();

    Map<String, String> actualResult = fileHelper.getNameAndRelativePath(rootFilePath);

    Map<String, String> expectedResult = populateNamesAndRelativePaths();
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void relativeAndAbsolutePathsAreReturned() {
    FileHelper fileHelper = new FileHelper();

    ArrayList<String> actualResult = fileHelper.getRelativeFilePaths(rootFilePath);

    ArrayList<String> expectedResult = populateRelativePaths();
    Collections.sort(actualResult);
    Collections.sort(expectedResult);
    assertTrue(expectedResult.equals(actualResult));
  }

  @Test
  public void fileBytesAreReturned(){
    FileHelper fileHelper = new FileHelper();
    String filePath = rootFilePath + "/result.txt";

    byte[] actualResult = fileHelper.readBytes(filePath);

    Path path = Paths.get(filePath);
    byte[] expectedResult = null;
    try {
      expectedResult = Files.readAllBytes(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertTrue(Arrays.equals(expectedResult, actualResult));
  }

  @Test
  public void fileHashValueIsReturned(){
    FileHelper fileHelper = new FileHelper();

    String filePath = rootFilePath + "/result.txt";
    String actualResult = fileHelper.hashValue(filePath);

    String expectedResult = "cc640aa14e96c7e21003963620c42259125749d9";
    assertEquals(expectedResult, actualResult);
  }

  private void resetFileToOriginalContents(String filePath, String originalFileContent){
    FileHelper fileHelper = new FileHelper();
    fileHelper.write(originalFileContent, filePath);
  }

  @Test
  public void aFileIsWrittenTo(){
    FileHelper fileHelper = new FileHelper();
    String filePath = rootFilePath + "/result.txt";
    String originalFileContent = new String(fileHelper.readBytes(filePath));

    String contentToWrite = "foobar";
    fileHelper.write(contentToWrite, filePath);

    String fileContents = new String(fileHelper.readBytes(filePath));
    assertEquals("foobar", fileContents);
    resetFileToOriginalContents(filePath, originalFileContent);
  }

  @Test
  public void partialFileContentsAreReturned(){
    FileHelper fileHelper = new FileHelper();

    String filePath = rootFilePath + "/result.txt";
    Integer startRange = 5;
    Integer endRange = 22;
    String partialContent = fileHelper.getPartialContents(filePath, startRange, endRange);

    assertEquals("e TimeLogger\nend\n", partialContent);
  }

  @Test
  public void fileLengthIsReturned(){
    FileHelper fileHelper = new FileHelper();

    String filePath = rootFilePath + "/result.txt";
    Integer actualResult = fileHelper.getLength(filePath);

    assertEquals(Integer.valueOf(22), actualResult);
  }

  @Test
  public void mimeTypeForPlainTextIsReturned(){
    FileHelper fileHelper = new FileHelper();

    String filePath = rootFilePath + "/result.txt";
    String actualResult = fileHelper.getMimeType(filePath);

    assertEquals("text/plain", actualResult);
  }

  @Test
  public void mimeTypeForPngIsReturned(){
    FileHelper fileHelper = new FileHelper();

    String filePath = rootFilePath + "/image.png";
    String actualResult = fileHelper.getMimeType(filePath);

    assertEquals("image/png", actualResult);
  }
}
