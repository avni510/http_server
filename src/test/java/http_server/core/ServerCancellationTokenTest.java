package http_server.core;

import http_server.core.ServerCancellationToken;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ServerCancellationTokenTest {

  @Test
  public void returnsTheListeningCondition() throws Exception {
    ServerCancellationToken serverCancellationToken = new ServerCancellationToken();
    serverCancellationToken.setListeningCondition(true);

    assertTrue(serverCancellationToken.isListening());
  }

  @Test
  public void returnsFalseForTheListeningCondition() throws Exception {
    ServerCancellationToken serverCancellationToken = new ServerCancellationToken();
    serverCancellationToken.setListeningCondition(true);
    serverCancellationToken.cancel();

    assertFalse(serverCancellationToken.isListening());
  }
}