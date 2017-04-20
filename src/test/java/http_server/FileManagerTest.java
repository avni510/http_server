package http_server;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class FileManagerTest {
  private FileManager fileManager;
  private String rootFilePath;

  @Before
  public void setup() {
    rootFilePath = "/Users/avnikothari/Desktop/resident_apprenticeship/java/http_server/code";
    fileManager = new FileManager();
  }

  @Test
  public void testFileNamesAndRelativePathsAreReturned() {
    Map<String, String> expectedNameAndRelativePath = new HashMap();
    expectedNameAndRelativePath.put("log_time_entry.txt", "/code/log_time_entry.txt");
    expectedNameAndRelativePath.put("result.txt", "/code/result.txt");
    expectedNameAndRelativePath.put("validation.txt", "/code/validation.txt");

    Map<String, String> actualNameAndRelativePath = fileManager.getNameAndRelativePath(rootFilePath);

    assertEquals(expectedNameAndRelativePath, actualNameAndRelativePath);

  }

  @Test
  public void testFileAbsolutePathsAreReturned() {
    String expectedAbsolutePath = "/Users/avnikothari/Desktop/resident_apprenticeship/java/http_server/code/log_time_entry.txt";

    String relativePath = "/code/log_time_entry.txt";
    String actualAbsolutePath = fileManager.getAbsolutePath(relativePath, rootFilePath);

    assertEquals(expectedAbsolutePath, actualAbsolutePath);
  }
}
