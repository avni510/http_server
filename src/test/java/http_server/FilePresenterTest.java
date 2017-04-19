package http_server;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class FilePresenterTest {
  private File[] filesInDirectory;
  private FilePresenter filePresenter;

  @Before
  public void setup() {
    File rootFileDirectory = new File("/Users/avnikothari/Desktop/resident_apprenticeship/java/http_server/code/");
    filesInDirectory = rootFileDirectory.listFiles();
    filePresenter = new FilePresenter();
  }

  @Test
  public void testFileNamesAndPathsAreReturned() {
    Map<String, String> expectedFileInformation = new HashMap();
    expectedFileInformation.put("log_time_entry.txt", "/Users/avnikothari/Desktop/resident_apprenticeship/java/http_server/code/log_time_entry.txt");
    expectedFileInformation.put("result.txt", "/Users/avnikothari/Desktop/resident_apprenticeship/java/http_server/code/result.txt");
    expectedFileInformation.put("validation.txt", "/Users/avnikothari/Desktop/resident_apprenticeship/java/http_server/code/validation.txt");

    Map<String, String> actualFileInformation = filePresenter.getFileInformation(filesInDirectory);

    assertEquals(expectedFileInformation, actualFileInformation);

  }
}
