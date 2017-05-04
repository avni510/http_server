package http_server;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileManager {

  public Map<String, String> getNameAndRelativePath(String rootPath) {
    Map<String, String> fileInformation = new HashMap();
    File[] filesInDirectory = getFiles(rootPath);
    for (int index = 0; index < filesInDirectory.length; index++) {
      String filePath = filesInDirectory[index].getPath();
      String relativePath = getRelativePath(filePath, rootPath);
      fileInformation.put(filesInDirectory[index].getName(), relativePath);
    }
    return fileInformation;
  }

  public Map<String, String> getRelativeAndAbsolutePath(String rootPath) {
    Map<String, String> fileInformation = new HashMap();
    File[] filesInDirectory = getFiles(rootPath);
    for (int index = 0; index < filesInDirectory.length; index++) {
      String filePath = filesInDirectory[index].getPath();
      String relativePath = getRelativePath(filePath, rootPath);
      fileInformation.put(relativePath, filePath);
    }
    return fileInformation;
  }

  private File[] getFiles(String rootPath) {
    File rootFile = new File(rootPath);
    return rootFile.listFiles();
  }

  private String getRelativePath(String filePath, String rootPath){
    return filePath.replace(rootPath, "");
  }
}

