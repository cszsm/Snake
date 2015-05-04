package control;

import model.Game;
import view.GameView;

/**
 * Created by Zsolt on 2015.03.10..
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

    @Override
    public void run() {
        while (!stopSignal) {
            try {
                Thread.sleep(500);
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
