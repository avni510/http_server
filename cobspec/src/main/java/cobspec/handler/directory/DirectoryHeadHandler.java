package cobspec.handler.directory;

import core.Handler;
import core.HttpCodes;

import core.request.Request;

import core.response.Response;
import core.response.ResponseBuilder;

import java.io.IOException;

public class DirectoryHeadHandler implements Handler {

  public Response generate(Request request) throws IOException {
    Response response = new ResponseBuilder()
        .setStatusCode(HttpCodes.OK)
        .build();
    return response;
  }
}
