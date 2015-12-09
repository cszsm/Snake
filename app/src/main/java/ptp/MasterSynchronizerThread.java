package ptp;

import android.util.Log;

import connection.ConnectionProperties;
import connection.TransferThread;
import control.TimeManager;

/**
 * Created by zscse on 2015. 09. 29..
 */
public class MasterSynchronizerThread extends Thread {

    @Override
    public void run() {
        // Preparing for synchronization
        TransferThread transferThread = ConnectionProperties.getInstance().getTransferThread();

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

            Log.v("udp", "waiting for delay_req");
            while (wait_for_delay_req) {

                delay_req = (SynchronizerPacket) transferThread.getPacket();
                if(delay_req != null) {
                    Log.v("udp", "NOTNULL");
                    delay_req_time = TimeManager.getTime();
                    wait_for_delay_req = false;
                }
            }
            Log.v("udp", "delay_req received");

            // Sending delay_resp
            transferThread.write(new SynchronizerPacket(delay_req_time));
            Log.v("udp", "delay_resp sent");
        }

        long time = TimeManager.getTime();
        time += 1000;
        transferThread.write(new SynchronizerPacket(time));

        Log.i("sync", String.valueOf(time));
        while (TimeManager.getTime() < time);
    }
}
