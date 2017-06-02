package core;

import core.request.Request;

import core.response.Response;

public interface Middleware {
  Response call(Request request) throws Exception;
}
