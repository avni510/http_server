package http_server;

import http_server.request.Request;

import http_server.response.Response;

import java.io.IOException;

public interface Handler {
  Response generate(Request request) throws IOException;
}
