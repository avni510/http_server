package http_server;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileManager {


  public Map<String, String> getNameAndRelativePath(String rootFilePath) {
    File rootFile = new File(rootFilePath);
    File[] filesInDirectory = rootFile.listFiles();
    Map<String, String> fileInformation = new HashMap();
    for (int index = 0; index < filesInDirectory.length; index++) {
      String filePath = filesInDirectory[index].getPath();
      String relativePath = filePath.replace(rootFilePath, "/" + rootFile.getName());
      fileInformation.put(filesInDirectory[index].getName(), relativePath);
    }
    return fileInformation;
  }

  public String getAbsolutePath(String relativePath, String rootPath) {
    Map<String, String> fileInformation = getRelativeAndAbsolutePath(rootPath);
    return fileInformation.get(relativePath);
  }

  public Map<String, String> getRelativeAndAbsolutePath(String rootPath) {
    File rootFile = new File(rootPath);
    File[] filesInDirectory = rootFile.listFiles();
    Map<String, String> fileInformation = new HashMap();
    for (int index = 0; index < filesInDirectory.length; index++) {
      String filePath = filesInDirectory[index].getPath();
      String rootFileParentPath = rootFile.getParent();
      String relativePath = filePath.replace(rootFileParentPath, "");
      fileInformation.put(relativePath, filesInDirectory[index].getPath());
    }
    return fileInformation;
  }
}
