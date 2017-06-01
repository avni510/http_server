package http_server;

import java.util.HashMap;
import java.util.Map;

public class DataStore<T1, T2> {
  Map<T1, T2> data = new HashMap<>();

  public void storeEntry(T1 key, T2 value) {
    data.put(key, value);
  }

  public Map<T1, T2> getData(){
    return data;
  }

  public T2 getValue(T1 key) {
    return data.get(key);
  }

  public void clear(){
    data = new HashMap<>();
  }

  public boolean isStoreEmpty(){
    return data.isEmpty();
  }

  public int count(){
    return data.size();
  }

  public void delete(T1 key) {
    data.remove(key);
  }

  public boolean keyExists(T1 key){
    return data.containsKey(key);
  }
}

