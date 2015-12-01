package control;

import android.util.Log;

import connection.ConnectionProperties;
import connection.enumeration.DeviceType;
import model.Game;
import ptp.MasterSynchronizerThread;
import ptp.SlaveSynchronizerThread;
import view.GameView;

/**
 * Created by Zsolt on 2015.03.10..
 * <p/>
 * Controls the game's timing
 */
public class TimingThread extends Thread {

    private GameView gameView;
    private volatile boolean stopSignal;
    private volatile boolean pauseSignal;

    public TimingThread(GameView gameView) {
        this.gameView = gameView;
        stopSignal = false;
        pauseSignal = false;
    }

    /**
     * Steps the game periodically
     */
    @Override
    public void run() {

        Log.v("udp", "timing started");

        Thread synchronizerThread;
        if (ConnectionProperties.getInstance().getDeviceType() == DeviceType.SERVER) {
            Log.v("udp", "SERVER");
            synchronizerThread = new MasterSynchronizerThread();
        } else {
            Log.v("udp", "CLIENT");
            synchronizerThread = new SlaveSynchronizerThread();
        }
        Log.v("udp", "it really started");
        synchronizerThread.start();
        Log.v("udp", "sync thread started");

        try {
            synchronizerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.v("udp", "sync thread finished");

        while (!stopSignal) {
            try {
//                if (ConnectionProperties.getInstance().getDeviceType() != DeviceType.CLIENT) {
//                    Thread.sleep(1000);
//                } else {
//                    Thread.sleep(20);
//                }

                Thread.sleep(900);

                if (!pauseSignal) {
                    Game.getInstance().getGameManager().send();
                }

                Thread.sleep(100);

                if (!pauseSignal) {
                    Game.getInstance().getGameManager().step();
                    gameView.postInvalidate();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void requestStop() {
        stopSignal = true;
    }

    public void setPauseSignal(boolean signal) {
        pauseSignal = signal;
    }
}
