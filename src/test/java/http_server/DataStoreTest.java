package http_server;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataStoreTest {

  @Test
  public void dataIsStored(){
    DataStore dataStore = new DataStore();

    dataStore.storeEntry("data", "fatcat");

    Map<String, String> expectedResult = new HashMap<>();
    expectedResult.put("data", "fatcat");
    assertTrue(expectedResult.equals(dataStore.getData()));
  }

  @Test
  public void valueIsReturned(){
    DataStore dataStore = new DataStore();

    dataStore.storeEntry("data", "fatcat");

    assertEquals("fatcat", dataStore.getValue("data"));
  }

  @Test
  public void isEmpty(){
    DataStore dataStore = new DataStore();

    assertTrue(dataStore.isStoreEmpty());
  }

  @Test
  public void clearData(){
    DataStore dataStore = new DataStore();
    dataStore.storeEntry("data", "fatcat");

    dataStore.clear();

    assertTrue(dataStore.isStoreEmpty());
  }
}