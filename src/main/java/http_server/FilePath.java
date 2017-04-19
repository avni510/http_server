package http_server;

import java.io.File;

public class FilePath {
  private File rootDirectory;

  public FilePath(String rootDirectoryPath) {
    this.rootDirectory = new File(rootDirectoryPath);
  }

  public File[] getFilePaths() {
    return rootDirectory.listFiles();
  }
}
