package http_server;

import java.util.HashMap;
import java.util.Map;

public class DataStore {
  Map<String, String> data = new HashMap<>();

  public void storeEntry(String key, String value) {
    data.put(key, value);
  }

  public Map<String, String> getData(){
    return data;
  }

  public String getValue(String key){
   return data.get(key);
  }

  public void clear(){
    data = new HashMap<>();
  }

  public boolean isStoreEmpty(){
    return data.isEmpty();
  }
}
