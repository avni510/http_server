package core;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class DataStoreTest {

  @Test
  public void dataIsStored() {
    DataStore dataStore = new DataStore<String, String>();

    dataStore.storeEntry("data", "fatcat");

    Map<String, String> expectedResult = new HashMap<>();
    expectedResult.put("data", "fatcat");
    assertTrue(expectedResult.equals(dataStore.getData()));
  }

  @Test
  public void valueIsReturned() {
    DataStore dataStore = new DataStore<Integer, String>();

    dataStore.storeEntry(1, "foo");

    assertEquals("foo", dataStore.getValue(1));
  }

  @Test
  public void clearData() {
    DataStore dataStore = new DataStore<String, String>();
    dataStore.storeEntry("data", "fatcat");

    dataStore.clear();

    assertTrue(dataStore.isStoreEmpty());
  }

  @Test
  public void getCountOfAllEntriesInDataStore() {
    DataStore dataStore = new DataStore<Integer, String>();
    dataStore.storeEntry(1, "fatcat");

    Integer actualResult = dataStore.count();

    assertEquals(new Integer(1), actualResult);
  }

  @Test
  public void anEntryIsDeleted() {
    DataStore dataStore = new DataStore<String, String>();
    dataStore.storeEntry("data", "fatcat");

    dataStore.delete("data");

    assertTrue(dataStore.isStoreEmpty());
  }

  @Test
  public void returnsTrueIfAValueExists() {
    DataStore dataStore = new DataStore<Integer, String>();
    dataStore.storeEntry(1, "foo");

    boolean result = dataStore.keyExists(1);

    assertTrue(result);
  }
}