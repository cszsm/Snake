package ptp;

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
        TransferThread transferThread = new TransferThread(ConnectionManager.getInstance().getSocket());
        transferThread.start();

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Sending sync
        transferThread.write(new SynchronizerPacket(TimeManager.getTime()));

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
}
