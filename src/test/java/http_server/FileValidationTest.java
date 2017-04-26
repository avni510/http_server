package http_server;

import org.junit.Test;

import static org.junit.Assert.*;

public class FileValidationTest {

  @Test
  public void testFileRelativePathExists(){
    FileValidation fileValidation = new FileValidation(new FileManager());
    String rootDirectoryPath = "/Users/avnikothari/Desktop/resident_apprenticeship/java/http_server/code";
    String requestUri = "/code/result.txt";
    boolean actualResult = fileValidation.hasRelativePath(rootDirectoryPath, requestUri);
    assertTrue(actualResult);
  }

  @Test
  public void testFileRelativePathDoesNotExist(){
    FileValidation fileValidation = new FileValidation(new FileManager());
    String rootDirectoryPath = "/Users/avnikothari/Desktop/resident_apprenticeship/java/http_server/code";
    String requestUri = "/code/time_logger.txt";
    boolean actualResult = fileValidation.hasRelativePath(rootDirectoryPath, requestUri);
    assertFalse(actualResult);
  }
}