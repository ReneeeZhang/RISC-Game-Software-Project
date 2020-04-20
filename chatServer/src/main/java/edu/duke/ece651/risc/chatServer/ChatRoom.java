package edu.duke.ece651.risc.chatServer;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Iterator;
import java.util.Set;

public class ChatRoom implements Runnable {
  private Selector selector;

  public ChatRoom(List<SocketChannel> sockets) throws IOException{
    selector = Selector.open();
    for (SocketChannel sc : sockets) {
      if (sc.isConnected()) {
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ);
      } else {
        sockets.remove(sc);
      }
    }
  }
  
  public void run() {
    while (true) {
      try{
        while (selector.keys().size() > 1) {
          selector.select();
          Set<SelectionKey> keys = selector.selectedKeys();
          Iterator<SelectionKey> keyIterator = keys.iterator();
          while (keyIterator.hasNext()) {
            SelectionKey key = keyIterator.next();
            if (key.isReadable()) {
              SocketChannel sc = (SocketChannel) key.channel();
              // send to all
            }
            keyIterator.remove();
          }
        }  
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }
}
