package http_server;

import java.io.File;

public class FileHelper {
  private File rootDirectory;

  public FileHelper(String rootDirectoryPath) {
    this.rootDirectory = new File(rootDirectoryPath);
  }

  public File[] getFilesInDirectory() {
    return rootDirectory.listFiles();
  }
}
