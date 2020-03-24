package edu.duke.ece651.risc.server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import shared.GameBoard;

public class InitializerTest {
  @Test
  public void test_initGame() throws IOException {
    Initializer initer = new Initializer(2);
    GameBoard gb = initer.initGame();
    System.out.println(gb.draw());
  }
}
