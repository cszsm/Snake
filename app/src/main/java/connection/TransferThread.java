package connection;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Queue;

import test.TestPacket;

/**
 * Created by Zsolt on 2015.05.02..
 * <p/>
 * Transfers packages between devices
 */
public class TransferThread extends Thread {

    private final ConnectionSocket socket;
//    private final InputStream inputStream;
//    private final OutputStream outputStream;

    //        private Packet packet;
    private Queue<Packet> packets;
    //    private int arrivedPackets;
    private boolean stopSignal;

    public TransferThread(ConnectionSocket socket) {
        this.socket = socket;
//        InputStream tmpIn = null;
//        OutputStream tmpOut = null;

        packets = new LinkedList<>();
//        arrivedPackets = 0;

//        try {
//            tmpIn = socket.getInputStream();
//            tmpOut = socket.getOutputStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        inputStream = tmpIn;
//        outputStream = tmpOut;
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
//                    packet = PacketSerialization.deserialize(buffer);
                    Packet packet = PacketSerialization.deserialize(buffer);
                    packets.offer(packet);
                    Log.v("timer_sync", "received");
                    try {
                        SnakePacket snakePacket = (SnakePacket) packet;
                        Log.v("timer_sync", snakePacket.getDirection().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                    Log.v("packet", String.valueOf(((TestPacket) packet).getTimestamp()));
//                    Log.v("packets", "kap");
//                    arrivedPackets++;
                } catch (IOException | ClassNotFoundException e) {
//                    Log.v("packets", "itt rossz");
                    e.printStackTrace();
                }
            }

            try {
//                bytes = inputStream.read(buffer);
                bytes = socket.receive(buffer);
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
//            outputStream.write(bytes);
            socket.send(bytes);
            Log.v("timer_sync", "sent");

            try {
                SnakePacket snakePacket = (SnakePacket) packet;
                Log.v("timer_sync", snakePacket.getDirection().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns with the last accepted package
     */
    public Packet getPacket() {
//        if (0 < arrivedPackets) {
//            arrivedPackets--;
//            return packet;
//            Log.v("fifo", String.valueOf(arrivedPackets) + String.valueOf(packets.size()));
        Log.v("timer_sync", "got");
        Packet packet = packets.poll();
        try {
            SnakePacket snakePacket = (SnakePacket) packet;
            Log.v("timer_sync", snakePacket.getDirection().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packet;
//        } else {
//            return null;
//        }
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
