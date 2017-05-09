package http_server;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ParameterHandlerTest {

  private String setUri(){
    return "/parameters?variable_1=Operators" +
          "%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%" +
          "20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all" +
          "%22%3F&variable_2=stuff";
  }

  private String getBody(){
   return "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"? variable_2 = stuff ";
  }

  @Test
  public void uriIsDecoded() throws Exception {
    Request request = new RequestBuilder()
        .setRequestMethod(RequestMethod.GET)
        .setUri(setUri())
        .setHttpVersion("HTTP/1.1")
        .setHeader(new ArrayList<>(Arrays.asList("Host: localhost")))
        .build();
    ParameterHandler parameterHandler = new ParameterHandler();

    Response actualResponse = parameterHandler.generate(request);

    assertEquals(getBody(), new String (actualResponse.getBody()));
  }
}