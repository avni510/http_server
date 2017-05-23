package http_server;

import java.util.Map;

public class FileMiddleware implements Middleware{
  private String rootDirectoryPath;
  private Middleware nextMiddleware;

  public FileMiddleware(String rootDirectoryPath, Middleware nextMiddleware){
    this.rootDirectoryPath = rootDirectoryPath;
    this.nextMiddleware = nextMiddleware;
  }

  public Response call(Request request) throws Exception {
    FileHelper fileHelper = new FileHelper();
    Map<String, String> relativeAndAbsolutePaths = fileHelper.getRelativeAndAbsolutePath(rootDirectoryPath);
    if (isValidRequest(relativeAndAbsolutePaths, request)){
        String absolutePath = rootDirectoryPath + request.getUri();
        return getFileHandlerResponse(request, fileHelper, absolutePath);
    } else if (fileExistsInDirectory(relativeAndAbsolutePaths, request)) {
      return methodNotAllowed(request);
    }
    return nextMiddleware.call(request);
  }

  private boolean isValidRequest(Map<String, String> relativeAndAbsolutePaths, Request request){
    return fileExistsInDirectory(relativeAndAbsolutePaths, request) && isValidRequestMethods(request);
  }

  private boolean fileExistsInDirectory(Map<String, String> relativeAndAbsolutePaths, Request request) {
    return relativeAndAbsolutePaths.containsKey(request.getUri());
  }

  private Response getFileHandlerResponse(Request request, FileHelper fileHelper, String absolutePath) throws Exception{
    Handler handler = new FileReaderHandler(absolutePath, fileHelper);
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