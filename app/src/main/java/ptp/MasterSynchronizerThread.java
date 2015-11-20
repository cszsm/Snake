package ptp;

import android.util.Log;

import connection.ConnectionManager;
import connection.TransferThread;
import control.TimeManager;
import test.TestPacket;

/**
 * Created by zscse on 2015. 09. 29..
 */
public class MasterSynchronizerThread extends Thread {

    @Override
    public void run() {
        // Preparing for synchronization
//        TransferThread transferThread = new TransferThread(ConnectionManager.getInstance().getSocket());
//        transferThread.start();
        TransferThread transferThread = ConnectionManager.getInstance().getTransferThread();

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 11; i++) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Sending sync
            transferThread.write(new SynchronizerPacket(TimeManager.getTime()));
            Log.v("udp", "sync packet sent");

            // Waiting for delay_req
            boolean wait_for_delay_req = true;
            SynchronizerPacket delay_req;
            long delay_req_time = 0;

            while (wait_for_delay_req) {
                delay_req = (SynchronizerPacket) transferThread.getPacket();
                if(delay_req != null) {
                    delay_req_time = TimeManager.getTime();
                    wait_for_delay_req = false;
                }
            }

            // Sending delay_resp
            transferThread.write(new SynchronizerPacket(delay_req_time));
        }

        long time = TimeManager.getTime();
        time += 1000;
        transferThread.write(new SynchronizerPacket(time));

        Log.v("sync", String.valueOf(time));
        while (TimeManager.getTime() < time);

//        transferThread.cancel();
//        ConnectionManager.getInstance().setTransferThread(transferThread);
    }
}
