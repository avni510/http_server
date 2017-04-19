package http_server;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilePresenter {

  public Map<String, String> getFileInformation(File[] filesInDirectory) {
    Map<String, String> fileInformation = new HashMap();
    for (int index = 0; index < filesInDirectory.length; index++) {
      fileInformation.put(filesInDirectory[index].getName(), filesInDirectory[index].getPath());
    }
    return fileInformation;
  }
}
