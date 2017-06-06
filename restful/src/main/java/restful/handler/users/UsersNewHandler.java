package restful.handler.users;

import core.DataStore;
import core.Handler;
import core.request.Request;
import core.response.Response;
import core.response.ResponseBuilder;

import javax.xml.crypto.Data;
import java.io.IOException;

public class UsersNewHandler implements Handler{

  public Response generate(Request request) throws IOException {
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .build();
  }
}
