package connection.bluetooth;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import connection.ConnectionSocket;
import connection.Packet;

/**
 * Created by Zsolt on 2015.05.03..
 * <p/>
 * Delegator class for BluetoothSocket
 * Implements ConnectionSocket
 */
public class BluetoothConnectionSocket implements ConnectionSocket {

    private BluetoothSocket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public BluetoothConnectionSocket(BluetoothSocket socket) {
        this.socket = socket;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public InputStream getInputStream() throws IOException {
//        return socket.getInputStream();
//    }
//
//    @Override
//    public OutputStream getOutputStream() throws IOException {
//        return socket.getOutputStream();
//    }

    @Override
    public void send(byte[] packet) throws IOException {
        outputStream.write(packet);
    }

    @Override
    public int receive(byte[] packet) throws IOException {
        return inputStream.read(packet);
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
