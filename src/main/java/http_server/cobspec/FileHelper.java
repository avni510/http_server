package http_server.cobspec;

import javax.xml.bind.DatatypeConverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.security.MessageDigest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import java.net.FileNameMap;
import java.net.URLConnection;


public class FileHelper {

  public Map<String, String> getNameAndRelativePath(String rootDirectoryPath) {
    Map<String, String> fileInformation = new HashMap();
    File[] filesInDirectory = getFiles(rootDirectoryPath);
    for (File file: filesInDirectory) {
      String fileAbsolutePath = file.getPath();
      String relativePath = getRelativePath(fileAbsolutePath, rootDirectoryPath);
      fileInformation.put(file.getName(), relativePath);
    }
    return fileInformation;
  }

  public ArrayList<String> getRelativeFilePaths(String rootDirectoryPath) {
    ArrayList<String> allRelativePaths = new ArrayList<>();
    File[] filesInDirectory = getFiles(rootDirectoryPath);
    for (File file: filesInDirectory) {
      String fileAbsolutePath = file.getPath();
      String relativePath = getRelativePath(fileAbsolutePath, rootDirectoryPath);
      allRelativePaths.add(relativePath);
    }
    return allRelativePaths;
  }

  public byte[] readBytes(String filePath) {
    Path file = Paths.get(filePath);
    byte[] fileBytes = null;
    try {
      fileBytes = Files.readAllBytes(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return fileBytes;
  }

  public String hashValue(String filePath) {
    try {
      byte[] fileBytes = readBytes(filePath);
      MessageDigest sha1Encoder = MessageDigest.getInstance("SHA-1");
      byte[] encodedBytes = sha1Encoder.digest(fileBytes);
      String hashValue = DatatypeConverter.printHexBinary(encodedBytes);
      return hashValue.toLowerCase();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public void write(String content, String filePath){
    try {
      FileWriter fileWriter = new FileWriter(filePath);
      BufferedWriter buffered = new BufferedWriter(fileWriter);
      buffered.write(content);
      buffered.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getPartialContents(String filePath, Integer startRange, Integer endRange) {
    byte[] fileBytes = readBytes(filePath);
    byte[] partialFileBytes = Arrays.copyOfRange(fileBytes, startRange, endRange);
    return new String(partialFileBytes);
  }

  public Integer getLength(String filePath) {
    File file = new File(filePath);
    Long fileSize = file.length();
    return fileSize.intValue();
  }

  public String getMimeType(String filePath){
    FileNameMap fileNameMap = URLConnection.getFileNameMap();
    return fileNameMap.getContentTypeFor(filePath);
  }

  private File[] getFiles(String rootPath) {
    File rootFile = new File(rootPath);
    return rootFile.listFiles();
  }

  private String getRelativePath(String filePath, String rootPath){
    return filePath.replace(rootPath, "");
  }
}

