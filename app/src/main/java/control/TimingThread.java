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

        synchronize();

        while (!stopSignal) {
            try {
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

    private void synchronize() {

        Thread synchronizerThread;
        if (ConnectionProperties.getInstance().getDeviceType() == DeviceType.SERVER) {
            synchronizerThread = new MasterSynchronizerThread();
        } else {
            synchronizerThread = new SlaveSynchronizerThread();
        }

        synchronizerThread.start();

        try {
            synchronizerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void requestStop() {
        stopSignal = true;
    }

    public void setPauseSignal(boolean signal) {
        pauseSignal = signal;
    }
}
