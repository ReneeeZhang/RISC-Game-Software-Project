package shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BaseRegionTestElegant {
  private Player p1;
  private Player p2;
  private BaseRegion br;
  
  @BeforeEach
  public void initPlayerAndRegion() {
    this.p1 = new Player("p1");
    this.p2 = new Player("p2");
    this.br = new BaseRegion("example", p1, 10);
  }
  
  @Test
  public void test_numUnitWithLevel() {
    assertTrue(br.numUnitWithLevel(p1, 0) == 5);
    assertTrue(br.numUnitWithLevel(p2, 0) == 0);
  }

  @Test
  public void test_sendUnit() {
    assertTrue(br.sendUnit(p1, 0, 1).size() == 1);
    assertTrue(br.sendUnit(p2, 0, 1).size() == 0);
  }

  @Test
  public void test_dispatch_success() {
    Region adj = new BaseRegion("adj", p1, 10);
    br.initOneBorderCamp(adj.getName());
    br.dispatch(adj.getName(), p1, 0, 1);
    assertTrue(br.getBorderCamp(adj.getName()).size() == 1);
  }

  @Test
  public void test_dispatch_failure() {
    Region adj = new BaseRegion("adj", p1, 10);
    br.initOneBorderCamp(adj.getName());
    br.dispatch(adj.getName(), p2, 0, 2);
    assertTrue(br.getBorderCamp(adj.getName()).size() == 0);
  }
}
