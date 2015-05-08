package control;

import connection.TransferThread;
import model.Snake;

/**
 * Created by Zsolt on 2015.03.14..
 * <p/>
 * An abstract class for managing other managers
 */
public abstract class GameManager {
    protected SnakeManager snakeManager;
    protected FoodManager foodManager;
    protected TransferThread transferThread;

    public GameManager(SnakeManager snakeManager, FoodManager foodManager) {
        this.snakeManager = snakeManager;
        this.foodManager = foodManager;
    }

    /**
     * Steps the game
     */
    public abstract void step();

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
