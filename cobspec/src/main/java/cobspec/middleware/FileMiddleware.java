package cobspec.middleware;

import cobspec.handler.file_reader.FileReaderGetHandler;
import cobspec.handler.file_reader.FileReaderPatchHandler;

import core.ErrorHandler;

import core.Middleware;
import cobspec.FileHelper;
import core.Handler;

import core.response.Response;

import core.request.Request;
import core.request.RequestMethod;

import java.util.ArrayList;

public class FileMiddleware implements Middleware {
  private String rootDirectoryPath;
  private Middleware app;

  public FileMiddleware(String rootDirectoryPath, Middleware app){
    this.rootDirectoryPath = rootDirectoryPath;
    this.app = app;
  }

  public Response call(Request request) throws Exception {
    FileHelper fileHelper = new FileHelper();
    ArrayList<String> relativePaths = fileHelper.getRelativeFilePaths(rootDirectoryPath);
    if (isValidRequest(relativePaths, request)){
        String absolutePath = rootDirectoryPath + request.getUri();
        return getFileHandlerResponse(request, fileHelper, absolutePath);
    } else if (fileExistsInDirectory(relativePaths, request)) {
      return methodNotAllowed(request);
    }
    return app.call(request);
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