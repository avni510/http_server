package restful.handler.users;

import core.DataStore;
import core.Handler;

import core.request.Request;

import core.response.Response;
import core.response.ResponseBuilder;

import java.io.IOException;
import java.io.StringWriter;

import javax.json.JsonWriter;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.Json;
import javax.json.JsonObject;

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
    } else if(uriHasId(request) && !request.getUri().contains("edit")) {
      return handleShow(request);
    } else if(request.getUri().equals("/users")){
      return handleIndex();
    }
    return null;
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
        .setBody(username)
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
    Map<Integer, String> allDataValues = dataStore.getData();
    StringWriter stringWriter = new StringWriter();
    JsonWriter jsonWriter = Json.createWriter(stringWriter);
    JsonObject jsonObject = Json.createObjectBuilder().add("users", populateUsers(allDataValues)).build();
    jsonWriter.writeObject(jsonObject);
    jsonWriter.close();
    return stringWriter.toString();
  }

  private JsonArray populateUsers(Map<Integer, String> allDataValues){
    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
    for (Map.Entry<Integer, String> data : allDataValues.entrySet()) {
      String id = String.valueOf(data.getKey());
      String username = data.getValue();
      jsonArrayBuilder.add(Json.createObjectBuilder().add("id", id).add("username", username));
    }
    return jsonArrayBuilder.build();
  }

  private boolean uriHasId(Request request) {
    return request.getIdInUri() == null ? false : true;
  }
}
