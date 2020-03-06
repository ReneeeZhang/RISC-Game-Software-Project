package edu.duke.ece651.risc;

import java.util.List;

public interface Board {
  public List<Region> getNeighbor(String name);
}
