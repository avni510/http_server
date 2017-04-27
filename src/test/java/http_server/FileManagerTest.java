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
    rootFilePath = System.getProperty("user.dir") + "/code";
  }

  @Test
  public void testFileNamesAndRelativePathsAreReturned() {
    fileManager = new FileManager();

    Map<String, String> actualNameAndRelativePath = fileManager.getNameAndRelativePath(rootFilePath);

    Map<String, String> expectedNameAndRelativePath = new HashMap();
    expectedNameAndRelativePath.put("log_time_entry.txt", "/code/log_time_entry.txt");
    expectedNameAndRelativePath.put("result.txt", "/code/result.txt");
    expectedNameAndRelativePath.put("validation.txt", "/code/validation.txt");
    assertEquals(expectedNameAndRelativePath, actualNameAndRelativePath);
  }

  @Test
  public void testFileAbsolutePathsAreReturned() {
    fileManager = new FileManager();

    String relativePath = "/code/log_time_entry.txt";
    String actualAbsolutePath = fileManager.getAbsolutePath(relativePath, rootFilePath);

    String expectedAbsolutePath = System.getProperty("user.dir") + "/code/log_time_entry.txt";
    assertEquals(expectedAbsolutePath, actualAbsolutePath);
  }
}
