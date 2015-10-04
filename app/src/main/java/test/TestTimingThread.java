package test;

import zsolt.cseh.snake.MultiplayerActivity;
import zsolt.cseh.snake.TestActivity;

/**
 * Created by zscse on 2015. 09. 28..
 */
public class TestTimingThread extends Thread {

    private TestActivity activity;

    public TestTimingThread(TestActivity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            activity.receivePacket();
        }
    }
}
