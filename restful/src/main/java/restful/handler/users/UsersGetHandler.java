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
      return handleEdit(request);
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
        .setHeader("Content-Type", "text/html")
        .setBody(createNewUserBody())
        .build();
  }

  private Response handleEdit(Request request) {
    Integer id = request.getIdInUri();
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeader("Content-Type", "text/html")
        .setBody(createEditUserBody(id))
        .build();
  }

  private Response handleShow(Request request) {
    Integer id = request.getIdInUri();
    String username = dataStore.getValue(id);
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeader("Content-Type", "text/html")
        .setBody(htmlToDisplayUsername(username))
        .build();
  }

  private Response handleIndex(){
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeader("Content-Type", "text/html")
        .setBody(getHtmlUsernamesTable())
        .build();
  }

  private String getHtmlUsernamesTable(){
    return "<table>" +
             "<tr>" +
               "<th>id</th>" +
               "<th>username</th>" +
             "</tr>" +
             getHtmlTableData() +
           "</table>";
  }

  private String getHtmlTableData(){
    StringBuilder htmlTableData = new StringBuilder();
    Map<Integer, String> allDataValues = dataStore.getData();
    for (Map.Entry<Integer, String> data : allDataValues.entrySet()) {
      Integer id = data.getKey();
      String username = data.getValue();
      htmlTableData.append(htmlTableDataTags(id, username));
    }
    return htmlTableData.toString();
  }

  private String htmlTableDataTags(Integer id, String username){
    return "<tr>" +
              "<td>" + String.valueOf(id) + "</td>" +
              "<td>" + username + "</td>" +
           "</tr>";
  }

  private String createNewUserBody(){
    return "<form action=\"/users\" method=\"post\">" +
              "Username: <br> " +
              "<input type=\"text\" name=\"username\">" +
              "<input type=\"submit\" value=\"Submit\">" +
            "</form> <br>";
  }

  private String createEditUserBody(Integer id){
    return "<form action=\"/users/" + String.valueOf(id) + "\">" +
              "Username: <br> " +
              "<input type=\"text\" name=\"username\">"+
              "<input type=\"submit\" value=\"Submit\">" +
            "</form> <br>";
  }

  private String htmlToDisplayUsername(String username){
    return "<p>" + username + "</p>";
  }

  private boolean uriHasId(Request request) {
    return request.getIdInUri() == null ? false : true;
  }
}
