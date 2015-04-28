package view;

import model.Game;

/**
 * Created by Zsolt on 2015.03.10..
 */
public class TimingThread extends Thread {

    private GameControl gameControl;
    private volatile boolean stopSignal;
    private volatile boolean pauseSignal;

    public TimingThread(GameControl gameControl) {
        this.gameControl = gameControl;
        stopSignal = false;
        pauseSignal = false;
    }

    @Override
    public void run() {
        while(!stopSignal){
            try {
                Thread.sleep(500);
                if(!pauseSignal) {
                    Game.getInstance().getGameManager().step();
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
