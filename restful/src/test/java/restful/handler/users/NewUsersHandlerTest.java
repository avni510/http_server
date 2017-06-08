package restful.handler.users;

import core.request.Request;
import core.request.RequestBuilder;
import core.request.RequestMethod;

import core.response.Response;

import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NewUsersHandlerTest {

  @Test
  public void aSuccessMessageIsReturned() throws IOException {
      Request request = new RequestBuilder()
              .setRequestMethod(RequestMethod.GET)
              .setUri("/users/new")
                            .setHeader("Host: localhost")
              .build();
      NewUsersHandler newUsersHandler = new NewUsersHandler();

      Response actualResponse = newUsersHandler.generate(request);

      assertEquals("200 OK", actualResponse.getStatusCodeMessage());
  }
}