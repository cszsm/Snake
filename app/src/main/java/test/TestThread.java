package test;

import android.util.Log;

import java.io.IOException;

import connection.Packet;
import connection.PacketSerialization;
import connection.TransferThread;
import model.enumeration.Direction;

/**
 * Created by Zsolt on 2015.04.14..
 */
public class TestThread extends Thread {

    private TransferThread transferThread;
    private Packet packet;

    public TestThread(TransferThread transferThread) {
        this.transferThread = transferThread;
        packet = new Packet(Direction.UP, 10, 10);
    }

    @Override
    public void run() {
        super.run();

        for(int i = 0; i < 10; i++) {
            try {
                Log.v("wtest", "sent");
                transferThread.write(PacketSerialization.serialize(packet));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                this.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
