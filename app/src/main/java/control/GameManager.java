package control;

import connection.wifi.TransferThread;
import model.Snake;

/**
 * Created by Zsolt on 2015.03.14..
 */
public abstract class GameManager {
    protected SnakeManager snakeManager;
    protected FoodManager foodManager;
    protected TransferThread transferThread;

    public GameManager(SnakeManager snakeManager, FoodManager foodManager) {
        this.snakeManager = snakeManager;
        this.foodManager = foodManager;
    }

    public abstract void step();

    public void step2() {}

    public Snake getSnake() {
        return snakeManager.getSnake();
    }

    public SnakeManager getSnakeManager() {
        return snakeManager;
    }

    public FoodManager getFoodManager() {
        return foodManager;
    }

    public void setTransferThread(TransferThread transferThread) {
        this.transferThread = transferThread;
    }
}
