package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public abstract class Connector {
  protected Socket socket;

  public Connector(String hostname, int port) throws IOException {
    SocketChannel sc = SocketChannel.open();
    sc.connect(new InetSocketAddress(hostname, port));
    this.socket = sc.socket();
  }

  protected Object receive() throws IOException, ClassNotFoundException {
    ObjectInputStream deserial = new ObjectInputStream(socket.getInputStream());
    return deserial.readObject();
  }

  protected void send(Object obj) throws IOException {
    ObjectOutputStream serial = new ObjectOutputStream(socket.getOutputStream());
    serial.writeObject(obj);
  }
}
