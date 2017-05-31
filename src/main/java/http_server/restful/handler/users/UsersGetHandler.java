package http_server.restful.handler.users;

import http_server.DataStore;

import http_server.Handler;

import http_server.request.Request;

import http_server.response.Response;
import http_server.response.ResponseBuilder;

import java.io.IOException;

import java.util.Map;

public class UsersGetHandler implements Handler{
  private DataStore dataStore;

  public UsersGetHandler(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  public Response generate(Request request) throws IOException {
    if(request.getUri().contains("new")) {
      return handleNew();
    } else if(request.getUri().contains("edit")) {
      return handleEdit(request);
    } else if(isLastElementInUriId(request)) {
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
    String[] uriParts = splitUri(request);
    String id = uriParts[uriParts.length - 2];
//    String username = dataStore.getValue(id);
    return new ResponseBuilder()
        .setHttpVersion("HTTP/1.1")
        .setStatusCode(200)
        .setHeader("Content-Type", "text/html")
        .setBody(createEditUserBody(id))
        .build();
  }

  private Response handleShow(Request request) {
    String[] uriParts = splitUri(request);
    String id = uriParts[uriParts.length - 1];
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

  private String[] splitUri(Request request) {
    String uri = request.getUri();
    return uri.split("/");
  }

  private String getHtmlTableData(){
    StringBuilder htmlTableData = new StringBuilder();
    Map<String, String> allDataValues = dataStore.getData();
    for (Map.Entry<String, String> data : allDataValues.entrySet()) {
      String id = data.getKey();
      String username = data.getValue();
      htmlTableData.append(
       "<tr>" +
         "<td>" + id + "</td>" +
         "<td>" + username + "</td>" +
       "</tr>"
      );
    }
    return htmlTableData.toString();
  }

  private String createNewUserBody(){
    return "<form action=\"/users\" method=\"post\">" +
              "Username: <br> " +
              "<input type=\"text\" name=\"username\">" +
              "<input type=\"submit\" value=\"Submit\">" +
            "</form> <br>";
  }

  private String createEditUserBody(String id){
    return "<form action=\"/users/" + id + "\" method=\"post\">" +
            "<input type=\"hidden\" name=\"_method\" value=\"put\"/>" +
              "Username: <br> " +
              "<input type=\"text\" name=\"username\">"+
              "<input type=\"submit\" value=\"Submit\">" +
            "</form> <br>";
  }

  private String htmlToDisplayUsername(String username){
    return "<p>" + username + "</p>";
  }

  private boolean isLastElementInUriId(Request request) {
    String[] uriParts = splitUri(request);
    String possibleId = uriParts[uriParts.length - 1];
    try {
      Integer.parseInt(possibleId);
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}
