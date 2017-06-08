package cobspec.handler;

import core.Handler;
import core.HttpCodes;

import core.request.Request;

import core.response.Response;
import core.response.ResponseBuilder;

import java.io.IOException;

public class RedirectGetHandler implements Handler {

  public Response generate(Request request) throws IOException {
    return new ResponseBuilder()
        .setStatusCode(HttpCodes.FOUND)
        .setHeader("Location", "/")
        .build();
  }
}
