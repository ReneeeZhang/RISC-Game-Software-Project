package edu.duke.ece651.risc.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import shared.BaseRegion;
import shared.GameBoard;
import shared.Region;

public class Initializer {
  private String configFilePath;

  public Initializer(int numPlayers) {
    this.configFilePath = "/config/" + numPlayers + "players_map.config";
  }

  public GameBoard initGame() throws IOException {
    InputStream is = getClass().getResourceAsStream(configFilePath);
    String allContent = inputStream2String(is);
    String[] configs = allContent.split("\n--\n");
    String playerRegionRelations = configs[0];
    String adjRegionRelations = configs[1];     
    // Parse playerRegionRelations
    Map<String, Region> regionNameMap = new HashMap<>();
    Map<String, List<Region>> playerRegionMap = new HashMap<>();
    parsePlayerRegionRelations(playerRegionRelations, regionNameMap, playerRegionMap);

    Map<Region, List<Region>> regionMap = generateRegionMap(adjRegionRelations, regionNameMap);
    return new GameBoard(regionMap, regionNameMap, playerRegionMap);
  }

  private String inputStream2String(InputStream is) throws IOException{
    ByteArrayOutputStream ans = new ByteArrayOutputStream();
    byte[] buffer = new byte[512];
    int len;
    while ((len = is.read(buffer)) != -1) {
      ans.write(buffer, 0, len);
    }
    return ans.toString();
  }

  
  private void parsePlayerRegionRelations(String playerRegionRelations, Map<String, Region> regionNameMap, Map<String, List<Region>> playerRegionMap) {
    Scanner sc = new Scanner(playerRegionRelations);
    while (sc.hasNextLine()) {
      String[] line = sc.nextLine().split(": ");
      String playerName = line[0];
      String[] regionNames = line[1].split(", ");
      List<Region> playerRegions = new ArrayList<>();
      for (int i = 0; i < regionNames.length; i++) {
        Region r = new BaseRegion(regionNames[i], playerName);
        playerRegions.add(r);
        regionNameMap.put(regionNames[i], r);
      }
      playerRegionMap.put(playerName, playerRegions);
    }
    sc.close();
  }

  private Map<Region, List<Region>> generateRegionMap(String adjRegionRelations, Map<String, Region> regionNameMap) {
    Scanner sc = new Scanner(adjRegionRelations);
    Map<Region, List<Region>> regionMap = new HashMap<>();
    while (sc.hasNextLine()) {
      String[] line = sc.nextLine().split(": ");
      String regionName = line[0];
      String[] adjRegionNames = line[1].split(", ");
      Region currRegion = regionNameMap.get(regionName);
      List<Region> adjRegions = new ArrayList<>();
      for (int i = 0; i < adjRegionNames.length; i++) {
        adjRegions.add(regionNameMap.get(adjRegionNames[i]));
        currRegion.initOneBorderCamp(adjRegionNames[i]);
      }
      regionMap.put(currRegion, adjRegions);
    }
    sc.close();
    return regionMap;
  }
}
