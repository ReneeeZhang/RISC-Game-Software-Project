package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class FakeChatServerForClient implements Runnable {
    private ServerSocketChannel ssc;
  private ObjectOutputStream serial;
  private ObjectInputStream deserial;
  
  public FakeChatServerForClient(int port){
    try {
      this.ssc = ServerSocketChannel.open();
      ssc.socket().bind(new InetSocketAddress(port));
      System.out.println("FakeChatServer binds on port: " + port);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  private void prepareIOStream() {
    try{
      SocketChannel sc = ssc.accept();
      System.out.println("FakeServer accepted");
      Socket socket = sc.socket();
      this.serial = new ObjectOutputStream(socket.getOutputStream());
      System.out.println("FakeServer OOS created");
      this.deserial = new ObjectInputStream(socket.getInputStream());
      System.out.println("FakeServer OIS created");
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  private void send(Object obj) throws IOException {
    serial.writeObject(obj);
    serial.flush();
  }

  private Object receive() throws IOException, ClassNotFoundException {
    return  deserial.readObject();
  }

  public void run() {
    try{
      prepareIOStream();
      int numPlayer = (int) receive();
      String msg1 = (String) receive();
      System.out.println("recv: " + msg1);
      send(msg1);
    } catch (IOException ex1) {
      ex1.printStackTrace();
    } catch (ClassNotFoundException ex2) {
      ex2.printStackTrace();
    }
  }
}
