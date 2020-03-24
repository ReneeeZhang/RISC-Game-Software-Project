package edu.duke.ece651.risc.server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import shared.GameBoard;

public class InitializerTest {
  @Test
  public void test_initGame() throws IOException {
    for (int i = 2; i < 6; i++) {
      Initializer initer = new Initializer(i);
      GameBoard gb = initer.initGame();
      System.out.println(gb.draw());
    }
  }
}
