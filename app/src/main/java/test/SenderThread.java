package test;

import android.util.Log;

import connection.TransferThread;
import control.TimeManager;
import zsolt.cseh.snake.TestActivity;

/**
 * Created by zscse on 2015. 10. 05..
 */
public class SenderThread extends Thread {

    private TransferThread thread;
    private TestActivity activity;

    public SenderThread(TransferThread thread, TestActivity activity) {
        this.thread = thread;
        this.activity = activity;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            TestPacket packet = activity.createPacket();
            thread.write(packet);
            Log.v("packet", "sent;" + packet.getSender().toString() + ";" + packet.getId() + ";" +
                    TimeManager.getTime(packet.getTimestamp()) + ";" + packet.getLength());
            try {
                sleep(20);
            } catch (InterruptedException e) {
                Log.v("error", e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
