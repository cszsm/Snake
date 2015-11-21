package connection;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Queue;

import control.TimeManager;
import ptp.SynchronizerPacket;
import test.TestPacket;

/**
 * Created by Zsolt on 2015.05.02..
 * <p/>
 * Transfers packages between devices
 */
public class TransferThread extends Thread {

    private final ConnectionSocket socket;

    private Queue<Packet> packets;
    private boolean stopSignal;

    public TransferThread(ConnectionSocket socket) {
        this.socket = socket;

        packets = new LinkedList<>();
        stopSignal = false;
    }

    /**
     * Accepts packages from other devices
     */
    @Override
    public void run() {
        byte[] buffer = new byte[2048];
        int bytes = 0;

        while (!stopSignal) {
            if (bytes != 0) {
                try {
                    Packet packet = PacketSerialization.deserialize(buffer);
                    packets.offer(packet);

//                    Log.v("timer_sync", "received");
//                    try {
//                        SnakePacket snakePacket = (SnakePacket) packet;
//                        Log.v("timer_sync", snakePacket.getDirection().toString());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            try {
                bytes = socket.receive(buffer);
                if(bytes != 0) {
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Send packages to another device
     */
    public void write(Packet packet) {
        byte[] bytes = new byte[0];

        try {
            bytes = PacketSerialization.serialize(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Log.v("size", String.valueOf(((TestPacket) packet).getId()) + " - " + String.valueOf(bytes.length));
        } catch (Exception e) {
            Log.v("size", "not testpacket --- " + e.getMessage());
        }

        try {
            socket.send(bytes);
//            Log.v("timer_sync", "sent");

//            try {
//                SnakePacket snakePacket = (SnakePacket) packet;
//                Log.v("timer_sync", snakePacket.getDirection().toString());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns with the last accepted package
     */
    public Packet getPacket() {
//        Log.v("timer_sync", "got");
        Packet packet = packets.poll();
//        try {
//            SnakePacket snakePacket = (SnakePacket) packet;
//            Log.v("timer_sync", snakePacket.getDirection().toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return packet;
    }

    public int getQueueLength() {
        return packets.size();
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
