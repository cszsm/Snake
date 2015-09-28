package test;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import connection.ConnectionSocket;
import connection.Packet;
import connection.PacketSerialization;

/**
 * Created by zscse on 2015. 09. 28..
 */
public class TestTransferThread extends Thread {

    private final ConnectionSocket socket;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    private TestPacket packet;
    private int arrivedPackets;
    private boolean stopSignal;

    public TestTransferThread(ConnectionSocket socket) {
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
        stopSignal = false;
    }

    /**
     * Accepts packages from other devices
     */
    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        int bytes = 0;
        Log.v("ttt", "run");

        while (!stopSignal) {
            Log.v("ttt", "!stop");
            if (bytes != 0) {
                Log.v("ttt", "if");
                try {
                    packet = TestPacketSerialization.deserialize(buffer);
                    arrivedPackets++;
                    Log.v("ttt", "serialization try");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    Log.v("ttt", "serialization catch");
                }
            }

            Log.v("ttt", "inputstream before try");
            try {
                Log.v("ttt", "inputstream try 1");
                bytes = inputStream.read(buffer);
                Log.v("ttt", "inputstream try 2");
            } catch (IOException e) {
                e.printStackTrace();
                Log.v("ttt", "inputstream catch");
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.v("ttt", "end !stop");
        }
    }

    /**
     * Send packages to another device
     */
    public void write(TestPacket packet) {
        byte[] bytes = new byte[0];

        try {
            bytes = TestPacketSerialization.serialize(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns with the last accepted package
     */
    public TestPacket getPacket() {
        if (0 < arrivedPackets) {
            arrivedPackets--;
            return packet;
        } else {
            return null;
        }
    }

    public void cancel() {
        stopSignal = true;

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
