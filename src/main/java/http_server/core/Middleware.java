package http_server.core;

import http_server.core.request.Request;

import http_server.core.response.Response;

public interface Middleware {
  Response call(Request request) throws Exception;
}
