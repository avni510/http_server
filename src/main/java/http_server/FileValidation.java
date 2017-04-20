package http_server;

import java.util.Map;
import java.util.Set;

public class FileValidation {
  private FileManager fileManager;

  public FileValidation(FileManager fileManager) {
    this.fileManager = fileManager;
  }

  public boolean hasRelativePath(String rootDirectoryPath, String requestUri) {
    Map<String, String> relativeAndAbsolutePaths = fileManager.getRelativeAndAbsolutePath(rootDirectoryPath);
    Set relativePaths = relativeAndAbsolutePaths.keySet();
    return relativePaths.contains(requestUri);
  }
}
