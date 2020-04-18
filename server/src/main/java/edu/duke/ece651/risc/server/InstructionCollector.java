package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shared.instructions.Instruction;

public class InstructionCollector {
  private Selector selector;
  
  public InstructionCollector(List<SocketChannel> playerSockets) throws IOException {
    selector = Selector.open();
    for (SocketChannel sc : playerSockets) {
      if (sc.isConnected()) {
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ);
      } else {
        playerSockets.remove(sc);
      }
    }
  }

  public Map<SocketChannel, List<Instruction>> collect() throws IOException {
    Map<SocketChannel, List<Instruction>> instrMap = new HashMap<SocketChannel, List<Instruction>>();
    while (selector.keys().size() > 1) {
      selector.select();
      Set<SelectionKey> keys = selector.selectedKeys();
      Iterator<SelectionKey> keyIterator = keys.iterator();
      while (keyIterator.hasNext()) {
        SelectionKey key = keyIterator.next();
        if (key.isReadable()) {
          key.cancel();
          SocketChannel sc = (SocketChannel) key.channel();
          sc.configureBlocking(true);
          List<Instruction> ins = recvInstructions(sc);
          instrMap.put(sc, ins);
        }
        keyIterator.remove();
      }
    }
    return instrMap;
  }

  private List<Instruction> recvInstructions(SocketChannel sc) throws IOException {
    Socket s = sc.socket();
    ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
    try {
      List<Instruction> ins = (ArrayList<Instruction>) deserial.readObject();
      return ins;
    } catch (ClassNotFoundException e) {
      System.out.println(e);
      return new ArrayList<Instruction>();
    }
  }
}
