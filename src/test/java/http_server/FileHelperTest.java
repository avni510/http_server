package http_server;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class FileHelperTest {

  @Test
  public void testArrayOfFilesIsReturned() {
    FileHelper fileHelper = new FileHelper("/Users/avnikothari/Desktop/resident_apprenticeship/java/http_server/code/");
    File rootDirectory = new File ("/Users/avnikothari/Desktop/resident_apprenticeship/java/http_server/code/");
    File[] expectedListOfFiles = rootDirectory.listFiles();
    File[] actualListOfFiles = fileHelper.getFilesInDirectory();

    assertEquals(expectedListOfFiles[0].getName(), actualListOfFiles[0].getName());

    Integer expectedLastIndex = expectedListOfFiles.length - 1;
    Integer actualLastIndex = actualListOfFiles.length - 1;

    assertEquals(expectedListOfFiles[expectedLastIndex].getName(), actualListOfFiles[actualLastIndex].getName());
  }
}