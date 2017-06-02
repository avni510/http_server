package restful.handler.users;

import core.DataStore;
import core.Handler;

import core.request.Request;

import core.response.Response;
import core.response.ResponseBuilder;

import java.io.IOException;

import java.util.Map;

public class UsersGetHandler implements Handler{
  private DataStore<Integer, String> dataStore;

  public UsersGetHandler(DataStore<Integer, String> dataStore) {
    this.dataStore = dataStore;
  }


  public Response generate(Request request) throws IOException {
    if(request.getUri().contains("new")) {
      return handleNew();
    } else if(request.getUri().contains("edit")) {
      return handleEdit();
    } else if(uriHasId(request)) {
      return handleShow(request);
    } else {
      return handleIndex();
    }
  }

  private Response handleNew() {
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .build();
  }

  private Response handleEdit() {
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .build();
  }

  private Response handleShow(Request request) {
    Integer id = request.getIdInUri();
    String username = dataStore.getValue(id);
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeader("Content-Type", "text/plain")
        .setBody(usernameDisplay(username))
        .build();
  }

  private Response handleIndex(){
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeader("Content-Type", "text/plain")
        .setBody(allUsernamesDisplay())
        .build();
  }

  private String allUsernamesDisplay(){
    StringBuilder htmlTableData = new StringBuilder();
    Map<Integer, String> allDataValues = dataStore.getData();
    for (Map.Entry<Integer, String> data : allDataValues.entrySet()) {
      Integer id = data.getKey();
      String username = data.getValue();
      htmlTableData.append(jsonIdAndUsername(id, username));
    }
    return htmlTableData.toString();
  }

  private String jsonIdAndUsername(Integer id, String username){
    return "{ \"id\": " + String.valueOf(id) + " { \"username\": " + username + "} }";
  }

  private String usernameDisplay(String username){
    return "{ \"username\": " + username + "}";
  }

  private boolean uriHasId(Request request) {
    return request.getIdInUri() == null ? false : true;
  }
}
