package connection.wifi;

import android.provider.ContactsContract;
import android.util.Log;

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
    private InetAddress broadcastAddress;
    int port;

    public WifiSocket(DatagramSocket socket, InetAddress broadcastAddress, int port) {
        this.socket = socket;
        this.broadcastAddress = broadcastAddress;
        this.port = port;
    }

    @Override
    public void send(byte[] packet) throws IOException {
//        String message = "Teszt";
//        byte[] bytes = message.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(packet, packet.length, broadcastAddress, port);
        socket.send(datagramPacket);
        Log.v("udp", "SENT - WifiSocket");
    }

    @Override
    public int receive(byte[] packet) throws IOException {
//        byte[] bytes = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(packet, packet.length);
        socket.receive(datagramPacket);
        Log.v("udp", "RECEIVED ADDRESS - " + datagramPacket.getAddress());

//        String message = new String(packet, 0, datagramPacket.getLength());
//        Log.v("udp", "RECEIVE" + message);
        Log.v("udp", "RECEIVED - WifiSocket");
        return datagramPacket.getLength();
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
