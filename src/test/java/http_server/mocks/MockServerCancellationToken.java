package http_server.mocks;

import http_server.CancellationToken;

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

  public void setListeningCondition(boolean listening) {
    this.listening = listening;
  }
}
