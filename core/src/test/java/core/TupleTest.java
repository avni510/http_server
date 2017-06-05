package core;

import core.request.RequestMethod;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class TupleTest {

  @Test
  public void firstElementIsReturned() throws Exception {
    Tuple<Enum<RequestMethod>, String> tuple = new Tuple<>(RequestMethod.GET, "/");

    Enum<RequestMethod> actualResult = tuple.getFirstElement();

    assertEquals(RequestMethod.GET, actualResult);
  }

  @Test
  public void secondElementIsReturned() throws Exception {
    Tuple<Enum<RequestMethod>, String> tuple = new Tuple<>(RequestMethod.GET, "/");

    String actualResult = tuple.getSecondElement();

    assertEquals("/", actualResult);
  }

  @Test
  public void twoTuplesWithTheSameValuesAreEqual() throws Exception {
    Tuple<Enum<RequestMethod>, String> tuple1 = new Tuple<>(RequestMethod.GET, "/");
    Tuple<Enum<RequestMethod>, String> tuple2 = new Tuple<>(RequestMethod.GET, "/");

    boolean actualResult = tuple1.equals(tuple2);

    assertTrue(actualResult);
  }

  @Test
  public void equalityIsSymmetric() throws Exception {
    Tuple<Enum<RequestMethod>, String> tuple1 = new Tuple<>(RequestMethod.GET, "/");
    Tuple<Enum<RequestMethod>, String> tuple2 = new Tuple<>(RequestMethod.GET, "/");

    boolean tuple1EqualsTuple2 = tuple1.equals(tuple2);
    boolean tuple2EqualsTuple1 = tuple2.equals(tuple1);

    assertEquals(tuple1EqualsTuple2, tuple2EqualsTuple1);
  }

  @Test
  public void theSameTupleIsEqual() throws Exception {
    Tuple<Enum<RequestMethod>, String> tuple1 = new Tuple<>(RequestMethod.GET, "/");

    boolean actualResult = tuple1.equals(tuple1);

    assertTrue(actualResult);
  }

  @Test
  public void twoTuplesWithDifferentValuesAreNotEqual() throws Exception {
    Tuple<Enum<RequestMethod>, String> tuple1 = new Tuple<>(RequestMethod.GET, "/");
    Tuple<Enum<RequestMethod>, String> tuple2 = new Tuple<>(RequestMethod.GET, "/code");

    boolean actualResult = tuple1.equals(tuple2);

    assertFalse(actualResult);
  }

  @Test
  public void twoDifferentObjectsAreNotEqual() throws Exception {
    Tuple<Enum<RequestMethod>, String> tuple = new Tuple<>(RequestMethod.GET, "/");
    Map<String, String> hashMap = new HashMap<>();

    boolean actualResult = tuple.equals(hashMap);

    assertFalse(actualResult);
  }

  @Test
  public void equalTuplesProduceTheSameHashCode() throws Exception {
    Tuple<Enum<RequestMethod>, String> tuple1 = new Tuple<>(RequestMethod.GET, "/");
    Tuple<Enum<RequestMethod>, String> tuple2 = new Tuple<>(RequestMethod.GET, "/");
    
    if (tuple1.equals(tuple2)) {
      assertTrue(tuple1.hashCode() == tuple2.hashCode());
    }
    else {
      System.out.println("tuples are not equal");
    }
  }
}