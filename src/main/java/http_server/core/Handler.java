package http_server.core;

import http_server.core.request.Request;

import http_server.core.response.Response;

import java.io.IOException;

public interface Handler {
  Response generate(Request request) throws IOException;
}
