package view;

import connection.ConnectionManager;
import connection.enumeration.DeviceType;
import model.Game;

/**
 * Created by Zsolt on 2015.04.02..
 */
public class MultiplayerTimingThread extends Thread {

    private MultiplayerView gameControl;
    private volatile boolean stopSignal;
    private volatile boolean pauseSignal;

    public MultiplayerTimingThread(MultiplayerView gameControl) {
        this.gameControl = gameControl;
        stopSignal = false;
        pauseSignal = false;
    }

    @Override
    public void run() {
        while (!stopSignal) {
            try {
                if (ConnectionManager.getInstance().getDeviceType() == DeviceType.MASTER)
                    Thread.sleep(1000);
                if (!pauseSignal) {
                    Game.getInstance().getGameManager().step();
//                    if(BluetoothManager.getInstance().getDeviceType() == DeviceType.MASTER) {
//                        Thread.sleep(100);
//                        Game.getInstance().getGameManager().step2();
//                    }
                    gameControl.postInvalidate();
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
