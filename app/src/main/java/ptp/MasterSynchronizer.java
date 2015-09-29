package ptp;

import connection.ConnectionManager;
import connection.ConnectionSocket;
import connection.TransferThread;
import control.TimeManager;

/**
 * Created by zscse on 2015. 09. 29..
 */
public class MasterSynchronizer {
    private ConnectionSocket socket;
    private TransferThread transferThread;
    private MasterSynchronizerThread receivingThread;

    public MasterSynchronizer() {
        socket = ConnectionManager.getInstance().getSocket();
        transferThread = new TransferThread(socket);
        receivingThread = new MasterSynchronizerThread();
    }

    public void synchronize() {
        // sync
        transferThread.write(new SynchronizerPacket(TimeManager.getTime()));

        // delay_rec

    }
}
