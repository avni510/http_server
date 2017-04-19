package http_server;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class FilePathTest {

  private List<String> convertFileToString(File[] filePaths) {
    List<String> pathsToFiles = new ArrayList<String>();
    for (int index = 0; index < filePaths.length; index++) {
      pathsToFiles.add(filePaths[index].getPath());
    }
    return pathsToFiles;
  }

  @Test
  public void testArrayOfFilePathsIsReturned() {
    FilePath filePath = new FilePath("/Users/avnikothari/Desktop/resident_apprenticeship/java/http_server/code/");

    List<String> expectedPathsToFiles = new ArrayList<String>();
    expectedPathsToFiles.add("/Users/avnikothari/Desktop/resident_apprenticeship/java/http_server/code/log_time_entry.txt");
    expectedPathsToFiles.add("/Users/avnikothari/Desktop/resident_apprenticeship/java/http_server/code/result.txt");
    expectedPathsToFiles.add("/Users/avnikothari/Desktop/resident_apprenticeship/java/http_server/code/validation.txt");

    List<String> actualPathsToFiles = convertFileToString(filePath.getFilePaths());

    assertEquals(expectedPathsToFiles, actualPathsToFiles);
  }
}