package cobspec.middleware;

import cobspec.FileHelper;

import cobspec.handler.file_reader.FileReaderGetHandler;
import cobspec.handler.file_reader.FileReaderPatchHandler;

import core.HttpCodes;
import core.Middleware;
import core.Handler;

import core.handler.ErrorHandler;

import core.request.Request;
import core.request.RequestMethod;

import java.util.ArrayList;

public class FileMiddleware implements Middleware {
  private String rootDirectoryPath;
  private Middleware app;

  public FileMiddleware(String rootDirectoryPath, Middleware app) {
    this.rootDirectoryPath = rootDirectoryPath;
    this.app = app;
  }

  public Handler call(Request request) throws Exception {
    FileHelper fileHelper = new FileHelper();
    ArrayList<String> relativePaths = fileHelper.getRelativeFilePaths(rootDirectoryPath);
    if (isValidRequest(relativePaths, request)) {
      String absolutePath = rootDirectoryPath + request.getUri();
      return getFileHandlerResponse(request, fileHelper, absolutePath);
    } else if (fileExistsInDirectory(relativePaths, request)) {
      return new ErrorHandler(HttpCodes.METHOD_NOT_ALLOWED);
    }
    return app.call(request);
  }

  private boolean isValidRequest(ArrayList<String> relativePaths, Request request) {
    return fileExistsInDirectory(relativePaths, request) && isValidRequestMethods(request);
  }

  private boolean fileExistsInDirectory(ArrayList<String> relativePaths, Request request) {
    return relativePaths.contains(request.getUri());
  }

  private Handler getFileHandlerResponse(Request request, FileHelper fileHelper, String absolutePath) throws Exception {
    Handler handler = null;
    if (request.getRequestMethod().equals(RequestMethod.PATCH)) {
      return new FileReaderPatchHandler(absolutePath, fileHelper);
    } else if (request.getRequestMethod().equals(RequestMethod.GET)) {
      return new FileReaderGetHandler(absolutePath, fileHelper);
    }
    return handler;
  }

  private boolean isValidRequestMethods(Request request) {
    return request.getRequestMethod().equals(RequestMethod.GET) ||
        request.getRequestMethod().equals(RequestMethod.PATCH);
  }
}