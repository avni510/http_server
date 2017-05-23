package http_server;

import javax.xml.bind.DatatypeConverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.security.MessageDigest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import java.net.FileNameMap;
import java.net.URLConnection;


public class FileHelper {

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

  public byte[] readBytes(String filePath)  {
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

