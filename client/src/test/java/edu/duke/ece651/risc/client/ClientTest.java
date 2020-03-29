package edu.duke.ece651.risc.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

public class ClientTest {
  @Test
  public void test_client() {
    System.out.println("Test client");
    Thread serverThread = new Thread(new FakeServer());
    serverThread.start();
    try {
      System.setIn(new FileInputStream("src/test/resources/ClientTest.txt"));
    } catch (FileNotFoundException e) {
      System.out.println(e);
    }
    try{
      Thread.sleep(50);
    } catch (InterruptedException e) {
      System.out.println(e);
    }
    Client.main(new String[] { "localhost", "6666" });
  }
}
