package edu.duke.ece651.risc.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

public class ClientTest {
  @Test
  public void test_client() {
    Thread serverThread = new Thread(new FakeServer());
    serverThread.start();
    try{
      System.setIn(new FileInputStream("src/test/resources/ClientTest.txt"));
    } catch (FileNotFoundException e) {
      System.out.println(e);
    }
    Client.main(new String[] { "localhost", "6666" });
  }
}
