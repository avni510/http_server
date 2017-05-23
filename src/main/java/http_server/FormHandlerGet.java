package http_server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FormHandlerGet implements Handler{
  private DataStore dataStore;
  private String parameter = "data";

  public FormHandlerGet(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  public Response generate(Request request) throws IOException {
    String bodyValue = getBodyValue();
    Response response = new ResponseBuilder()
              .setHttpVersion("HTTP/1.1")
              .setStatusCode(200)
              .setHeader("Content-Type", "text/html")
              .setBody(getBody(bodyValue))
              .build();
//    } else if(request.getRequestMethod() == RequestMethod.POST){
//      String parameterValue = request.getBodyParam(parameter);
//      dataStore.storeEntry(parameter, parameterValue);
//      response = new ResponseBuilder()
//          .setHttpVersion("HTTP/1.1")
//          .setStatusCode(200)
//          .build();
//    } else if(request.getRequestMethod() == RequestMethod.PUT){
//    String parameterValue = request.getBodyParam(parameter);
//    dataStore.storeEntry(parameter, parameterValue);
//    response = new ResponseBuilder()
//        .setHttpVersion("HTTP/1.1")
//        .setStatusCode(200)
//        .build();
//  } else if(request.getRequestMethod() == RequestMethod.DELETE){
//    dataStore.clear();
//    response = new ResponseBuilder()
//        .setHttpVersion("HTTP/1.1")
//        .setStatusCode(200)
//        .build();
//  }
    return response;
  }

  private String getBodyValue() {
    if (!dataStore.isStoreEmpty()){
      String parameterValue = dataStore.getValue(parameter);
      return parameter + "=" + parameterValue;
    } else {
      return "";
    }
  }

  private String getBody(String bodyValue) {
    return
        "<form action=\"/form\" method=\"post\">" +
          "Data: <br> " +
          "<input type=\"text\" name=\"" + parameter + "\">" +
          "<input type=\"submit\" value=\"Submit\">" +
        "</form> <br>" + "<p>" + bodyValue + "</p>";
  }
}
