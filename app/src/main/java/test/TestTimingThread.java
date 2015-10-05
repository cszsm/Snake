package test;

import android.util.Log;

import zsolt.cseh.snake.MultiplayerActivity;
import zsolt.cseh.snake.TestActivity;

/**
 * Created by zscse on 2015. 09. 28..
 */
public class TestTimingThread extends Thread {

    private TestActivity activity;

    private boolean stopSignal;

    public TestTimingThread(TestActivity activity) {
        this.activity = activity;
        stopSignal = false;
    }

    @Override
    public void run() {
        while (!stopSignal) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            activity.receivePacket();
        }
    }

    public void requestStop() {
        stopSignal = true;
    }
}
