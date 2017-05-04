package http_server;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class FileManagerTest {
  private String rootFilePath;

  @Before
  public void setup() {
    rootFilePath = System.getProperty("user.dir") + "/code";
  }

  private String getAbsolutePath(String filename){
    return System.getProperty("user.dir") + "/code" + filename;
  }

  private void addRelativeAndAbsolutePath(Map<String, String> paths, String relativeFilePath){
    paths.put(relativeFilePath, getAbsolutePath(relativeFilePath));
  }

  private void addNameAndRelativePath(Map<String, String> paths, String fileName ) {
    paths.put(fileName, "/" + fileName);
  }

  private Map<String, String> populateNamesAndRelativePaths(){
    Map<String, String> paths = new HashMap();
    addNameAndRelativePath(paths, "log_time_entry.txt");
    addNameAndRelativePath(paths, "result.txt");
    addNameAndRelativePath(paths, "validation.txt");
    return paths;
  }

  private Map<String, String> populateRelativeAndAbsolutePaths(){
    Map<String, String> paths = new HashMap();
    addRelativeAndAbsolutePath(paths, "/log_time_entry.txt");
    addRelativeAndAbsolutePath(paths, "/result.txt");
    addRelativeAndAbsolutePath(paths, "/validation.txt");
    return paths;
  }

  @Test
  public void testFileNamesAndRelativePathsAreReturned() {
    FileManager fileManager = new FileManager();

    Map<String, String> actualNameAndRelativePath = fileManager.getNameAndRelativePath(rootFilePath);

    Map<String, String> expectedNameAndRelativePath = populateNamesAndRelativePaths();
    assertEquals(expectedNameAndRelativePath, actualNameAndRelativePath);
  }

  @Test
  public void testRelativeAndAbsolutePathsAreReturned() {
    FileManager fileManager = new FileManager();

    Map<String, String> actualRelativeAndAbsolutePath = fileManager.getRelativeAndAbsolutePath(rootFilePath);

    Map<String, String> expectedRelativeAndAbsolutePath = populateRelativeAndAbsolutePaths();
    assertEquals(expectedRelativeAndAbsolutePath, actualRelativeAndAbsolutePath);
  }
}
