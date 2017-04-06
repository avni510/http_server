package com.company;

import java.net.*;
import java.io.*;

public class HTTPServer {

  public static void main(String args[]) throws Exception {
    ServerSocket serverSocket = new ServerSocket(4444);
    System.out.println("Listening for connection on port 4444");
    while(true) {
      try (Socket clientSocket = serverSocket.accept()) {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String line = in.readLine();
        while (!line.isEmpty()) {
          System.out.println(line);
          line = in.readLine();
        }
        String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + "hello world";
        clientSocket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
      }
    }
  }
}
