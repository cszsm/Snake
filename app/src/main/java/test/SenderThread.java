package test;

import android.util.Log;

import connection.TransferThread;
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
            thread.write(activity.createPacket());
            try {
                sleep(50);
            } catch (InterruptedException e) {
                Log.v("error", e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
