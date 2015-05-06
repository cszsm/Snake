package connection;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;

import connection.ConnectionSocket;
import connection.Packet;
import connection.PacketSerialization;

/**
 * Created by Zsolt on 2015.05.02..
 *
 * Transfers packages between devices
 */
public class TransferThread extends Thread {
    private final ConnectionSocket socket;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    private Packet packet;
    private int arrivedPackets;

    public TransferThread(ConnectionSocket socket) {
        this.socket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        arrivedPackets = 0;

        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        inputStream = tmpIn;
        outputStream = tmpOut;
    }

    /** Accepts packages from other devices */
    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        int bytes = 0;

        while (true) {
            if(bytes != 0) {
                try {
                    packet = PacketSerialization.deserialize(buffer);
                    arrivedPackets++;
                } catch (IOException e) {
                    Log.v("wifi", e.toString());
                } catch (ClassNotFoundException e) {
                    Log.v("wifi", "ClassNotFoundExcpetion");
                }
            }
            try {
                bytes = inputStream.read(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** Send packages to another device */
    public void write(byte[] bytes) {
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Returns with the last accepted package */
    public Packet getPacket() {
        if(0 < arrivedPackets){
            arrivedPackets--;
            return packet;
        } else {
            return null;
        }
    }

    public void cancel() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
