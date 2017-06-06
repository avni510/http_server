package restful.handler.users;

import core.DataStore;
import core.Handler;

import core.request.Request;

import core.response.Response;
import core.response.ResponseBuilder;

import javax.json.JsonWriter;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.Json;
import javax.json.JsonObject;

import java.io.IOException;
import java.io.StringWriter;

import java.util.Map;

public class IndexUsersHandler implements Handler{
  private DataStore<Integer, String> dataStore;

  public IndexUsersHandler(DataStore dataStore){
    this.dataStore = dataStore;
  }

  public Response generate(Request request) throws IOException {
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
}
