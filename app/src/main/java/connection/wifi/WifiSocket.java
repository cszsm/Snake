package connection.wifi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import connection.ConnectionSocket;

/**
 * Created by zscse on 2015. 10. 12..
 */
public class WifiSocket implements ConnectionSocket {

    private DatagramSocket socket;
    private InetAddress address;
    int port;

    public WifiSocket(DatagramSocket socket, InetAddress address, int port) {
        this.socket = socket;
        this.address = address;
        this.port = port;
    }

    /**
     * Sends a packet to the remote device
     */
    @Override
    public void send(byte[] packet) throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(packet, packet.length, address, port);
        socket.send(datagramPacket);
    }

    /**
     * Receive a packet from the remote device
     */
    @Override
    public int receive(byte[] packet) throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(packet, packet.length);
        socket.receive(datagramPacket);
        return datagramPacket.getLength();
    }

    /**
     * Closes the socket
     */
    @Override
    public void close() throws IOException {
        socket.close();
    }
}
