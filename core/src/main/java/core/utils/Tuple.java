package core.utils;

public class Tuple<T1, T2> {
  public final T1 t1;
  public final T2 t2;

  public Tuple(T1 t1, T2 t2) {
    this.t1 = t1;
    this.t2 = t2;
  }

  public T1 getFirstElement() {
    return t1;
  }

  public T2 getSecondElement() {
    return t2;
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    }
    if (!(object instanceof Tuple)) {
      return false;
    }
    Tuple tuple = (Tuple) object;
    return tuple.getFirstElement().equals(t1) &&
        tuple.getSecondElement().equals(t2);
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 31 * hash + t1.hashCode();
    hash = 31 * hash + t2.hashCode();
    return hash;
  }
}
