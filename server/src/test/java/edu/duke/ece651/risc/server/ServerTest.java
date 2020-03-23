package edu.duke.ece651.risc.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class ServerTest {
  @Test
  public void test_server() {
    Scanner sc1 = new Scanner("Bostock\nTeer\n5\n");
    Scanner sc2 = new Scanner("Hudson\nWilson\n2\n");
    Thread player1 = new Thread(new FakeClient(sc1));
    Thread player2 = new Thread(new FakeClient(sc2));
    player1.start();
    player2.start();
    try{
      System.setIn(new FileInputStream("src/test/resources/ServerTest.txt"));
    } catch (FileNotFoundException e) {
      System.out.println(e);
    }
    Server.main(new String[] { "6666" });
  }
}
