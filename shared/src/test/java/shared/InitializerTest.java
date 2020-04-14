package shared;

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
      //assertTrue(gb.getRegion("Hudson").getSize() == 10);
    }
  }
}
