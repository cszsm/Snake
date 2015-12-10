package connection;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Zsolt on 2015.05.02..
 * <p/>
 * Transfers packages between devices
 */
public class TransferThread extends Thread {

    private final ConnectionSocket socket;

    private Queue<Serializable> packets;
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
                    Serializable packet = PacketSerialization.deserialize(buffer);
                    packets.offer(packet);

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            try {
                bytes = socket.receive(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Send packages to another device
     */
    public void write(Serializable packet) {
        byte[] bytes = new byte[0];

        try {
            bytes = PacketSerialization.serialize(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            socket.send(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns with the last accepted package
     */
    public Serializable getPacket() {
        return packets.poll();
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
