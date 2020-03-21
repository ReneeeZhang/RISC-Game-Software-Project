package edu.duke.ece651.risc.server;

import java.io.DataInputStream;
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

import shared.Instruction;

public class InstructionCollector {
  private Selector selector;
  
  public InstructionCollector(List<SocketChannel> playerSockets) throws IOException {
    selector = Selector.open();
    for (SocketChannel sc : playerSockets) {
      sc.configureBlocking(false);
      sc.register(selector, SelectionKey.OP_READ);
    }
  }

  public Map<SocketChannel, List<Instruction>> collect() throws IOException {
    Map<SocketChannel, List<Instruction>> instrMap= new HashMap<SocketChannel, List<Instruction>>();
    while (selector.keys().size()>0) {
      selector.select();
      Set<SelectionKey> keys = selector.selectedKeys();
      Iterator<SelectionKey> keyIterator = keys.iterator();
      while (keyIterator.hasNext()) {
        SelectionKey key = keyIterator.next();
        if (key.isReadable()) {
          List<Instruction> instr = recvInstruction(key);
          instrMap.put((SocketChannel) key.channel(), instr);
          key.cancel();
        }
        keyIterator.remove();
      }
    }
    return instrMap;
  }

  private List<Instruction> recvInstruction(SelectionKey key) throws IOException {
    List<Instruction> ins = new ArrayList<Instruction>();
    SocketChannel sc = (SocketChannel) key.channel();
    Socket s = sc.socket();
    DataInputStream din = new DataInputStream(s.getInputStream());
    ObjectInputStream deserial = new ObjectInputStream(din);
    try {
      // TODO: List<Instruction>
      Instruction instr = (Instruction) deserial.readObject();
      System.out.println(instr);
      ins.add(instr);
    } catch (ClassNotFoundException e) {
      System.out.println(e);
    }
    return ins;
  }
}
