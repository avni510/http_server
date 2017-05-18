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
    for (Map.Entry<String, String> path : relativeAndAbsolutePaths.entrySet()) {
      String relativeFilePath = path.getKey();
      if (isValidFileRoute(request, relativeFilePath)) {
        return getFileHandlerResponse(request, fileHelper, path.getValue());
      } else if(filePathExists(request, relativeFilePath)) {
        return methodNotAllowed(request);
      }
    }
    return nextMiddleware.call(request);
  }

  private Response getFileHandlerResponse(Request request, FileHelper fileHelper, String absolutePath) throws Exception{
    Handler handler = new FileReaderHandler(absolutePath, fileHelper);
    return handler.generate(request);
  }

  private boolean filePathExists(Request request, String relativeFilePath){
    return request.getUri().equals(relativeFilePath);
  }

  private boolean isValidFileRoute(Request request, String relativeFilePath){
    return filePathExists(request, relativeFilePath) && isValidRequestMethods(request);
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