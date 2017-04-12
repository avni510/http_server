package http_server;

public class MockServerCancellationToken implements CancellationToken {
  private int count = 0;

  public boolean isListening() {
    if (count == 0){
      count += 1;
      return true;
    } else {
      return false;
    }
  }
}
