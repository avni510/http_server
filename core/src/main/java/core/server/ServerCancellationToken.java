package core.server;

import core.CancellationToken;

public class ServerCancellationToken implements CancellationToken {
  private boolean listening;

  public boolean isListening() {
    return listening;
  }

  public void cancel() {
    listening = false;
  }

  public void setListeningCondition(boolean listening) {
    this.listening = listening;
  }
}
