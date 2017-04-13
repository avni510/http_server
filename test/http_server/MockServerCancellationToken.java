package http_server;

public class MockServerCancellationToken implements CancellationToken {
  private boolean listening = true;
  private int count = 0;

  public boolean isListening() {
    if (count == 0){
      count += 1;
    } else {
      cancel();
    }
    return  listening;
  }

  public void cancel(){
    listening = false;
  }
}
