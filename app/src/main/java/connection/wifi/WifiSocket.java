package connection.wifi;

import android.provider.ContactsContract;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import connection.ConnectionSocket;

/**
 * Created by zscse on 2015. 10. 12..
 */
public class WifiSocket implements ConnectionSocket {

    private DatagramSocket socket;

    public WifiSocket(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void send(byte[] packet) throws IOException {

    }

    @Override
    public int receive(byte[] packet) throws IOException {
        return 0;
    }

    @Override
    public void close() throws IOException {

    }
}
