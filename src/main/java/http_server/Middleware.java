package http_server;


public interface Middleware {
  Response call(Request request) throws Exception;
}
