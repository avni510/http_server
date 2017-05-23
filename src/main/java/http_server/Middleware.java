package http_server;

import http_server.request.Request;

import http_server.response.Response;

public interface Middleware {
  Response call(Request request) throws Exception;
}
