package core;

import core.request.Request;

public interface Middleware {
  Handler call(Request request) throws Exception;
}
