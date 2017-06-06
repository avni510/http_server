package core;

import core.request.Request;

import core.response.Response;

public interface Middleware {
  Handler call(Request request) throws Exception;
}
