package http_server;

import java.io.*;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;

public class MockServerSocketConnection implements Connection {
  private List<Byte> storedOutputData = new ArrayList<>();
  private byte[] storedInputData;

  public InputStream in() throws IOException {
    return new ByteArrayInputStream(storedInputData);
  }

  public OutputStream out() throws IOException {
    return new OutputStream(){
      @Override
      public void write(int b) throws IOException {
        storedOutputData.add((byte) b);
      }
    };
  }

  public void setStoredInputData(String data){
    storedInputData = data.getBytes();
  }

  public String getStoredOutputData() {
    return bytesToString(listToByteArray(storedOutputData));
  }

  private byte[] listToByteArray(List<Byte> listOfBytes) {
    byte[] byteArray = new byte[listOfBytes.size()];
    int index = 0;
    for (byte b : listOfBytes) {
      byteArray[index++] = b;
    }
    return byteArray;
  }

  private String bytesToString(byte[] arrayOfBytes) {
    System.out.println(arrayOfBytes);
    return new String(arrayOfBytes, StandardCharsets.UTF_8);
  }
}
