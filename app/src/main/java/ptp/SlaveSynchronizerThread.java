package ptp;

import android.util.Log;

import java.sql.Connection;

import connection.ConnectionManager;
import connection.TransferThread;
import control.TimeManager;

/**
 * Created by zscse on 2015. 09. 29..
 */
public class SlaveSynchronizerThread extends Thread {

    @Override
    public void run() {
        // Preparing for synchronization
        TransferThread transferThread = new TransferThread(ConnectionManager.getInstance().getSocket());
        transferThread.start();

        // Waiting for sync
        boolean wait_for_sync = true;
        SynchronizerPacket sync = new SynchronizerPacket(0);
        long sync_time = 0;

        while (wait_for_sync) {
            sync = (SynchronizerPacket) transferThread.getPacket();
            if(sync != null) {
                sync_time = TimeManager.getTime();
                wait_for_sync = false;
            }
        }

        // Sending delay_req
        long delay_req_time = TimeManager.getTime();
        transferThread.write(new SynchronizerPacket(0));

        // Waiting for delay_resp
        boolean wait_for_delay_resp = true;
        SynchronizerPacket delay_resp;
        long delay_resp_time = 0;

        while (wait_for_delay_resp) {
            delay_resp = (SynchronizerPacket) transferThread.getPacket();
            if(delay_resp != null) {
                delay_resp_time = TimeManager.getTime();
                wait_for_delay_resp = false;
            }
        }

        long delay_1 = sync.getTime() - sync_time;
        long delay_2 = (delay_resp_time - delay_req_time) / 2;

        ConnectionManager.getInstance().setOffset(delay_1 + delay_2);

        Log.v("sync", "offset: " + ConnectionManager.getInstance().getOffset());
    }
}
