package edu.duke.ece651.risc.authServer;
import org.junit.jupiter.api.Test;

public class AuthServerTest {
  @Test
  public void test_AuthServer() {
    Thread fake1 = new Thread(new FakeAuthClient("user&&passw0rd", "yes"));
    Thread fake2 = new Thread(new FakeAuthClient("user&&password", "no"));
    fake1.start();
    fake2.start();
    try{
      AuthServer server = AuthServer.start("src/main/resources/config.txt");
      for (int i = 0; i < 2; i++) {
        server.handleRequest();
      }
    } catch (Exception e) {
    }
  }
}
