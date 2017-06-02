package core;

import core.request.Request;

import core.response.Response;

import java.io.IOException;

public interface Handler {
  Response generate(Request request) throws IOException;
}
