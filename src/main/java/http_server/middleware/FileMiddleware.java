package http_server.middleware;

import http_server.handler.file_reader.FileReaderGetHandler;
import http_server.handler.file_reader.FileReaderPatchHandler;
import http_server.handler.ErrorHandler;

import http_server.Middleware;
import http_server.FileHelper;
import http_server.Handler;

import http_server.response.Response;

import http_server.request.Request;
import http_server.request.RequestMethod;

import java.util.ArrayList;
import java.util.Map;

public class FileMiddleware implements Middleware {
  private String rootDirectoryPath;
  private Middleware nextMiddleware;

  public FileMiddleware(String rootDirectoryPath, Middleware nextMiddleware){
    this.rootDirectoryPath = rootDirectoryPath;
    this.nextMiddleware = nextMiddleware;
  }

  public Response call(Request request) throws Exception {
    FileHelper fileHelper = new FileHelper();
    ArrayList<String> relativeAndAbsolutePaths = fileHelper.getRelativeFilePaths(rootDirectoryPath);
    if (isValidRequest(relativeAndAbsolutePaths, request)){
        String absolutePath = rootDirectoryPath + request.getUri();
        return getFileHandlerResponse(request, fileHelper, absolutePath);
    } else if (fileExistsInDirectory(relativeAndAbsolutePaths, request)) {
      return methodNotAllowed(request);
    }
    return nextMiddleware.call(request);
  }

  private boolean isValidRequest(ArrayList<String> relativePaths, Request request){
    return fileExistsInDirectory(relativePaths, request) && isValidRequestMethods(request);
  }

  private boolean fileExistsInDirectory(ArrayList<String> relativePaths, Request request) {
    return relativePaths.contains(request.getUri());
  }

  private Response getFileHandlerResponse(Request request, FileHelper fileHelper, String absolutePath) throws Exception{
    Handler handler = null;
    if (request.getRequestMethod().equals(RequestMethod.PATCH)){
      handler = new FileReaderPatchHandler(absolutePath, fileHelper);
    } else if (request.getRequestMethod().equals(RequestMethod.GET)) {
      handler = new FileReaderGetHandler(absolutePath, fileHelper);
    }
    return handler.generate(request);
  }

  private boolean isValidRequestMethods(Request request){
    return request.getRequestMethod().equals(RequestMethod.GET) ||
           request.getRequestMethod().equals(RequestMethod.PATCH);
  }

  private Response methodNotAllowed(Request request) throws Exception{
    Handler handler = new ErrorHandler(405);
    return handler.generate(request);
  }
}