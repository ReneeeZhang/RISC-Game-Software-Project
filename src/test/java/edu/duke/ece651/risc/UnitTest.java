package edu.duke.ece651.risc;

import java.io.EOFException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class UnitTest {
  @Test
  public void test_client() {
    try{
      Client client = new Client();
      //System.out.println("Connect");
      client.receive();
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}