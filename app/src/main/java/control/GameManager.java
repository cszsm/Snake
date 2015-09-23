package control;

import connection.TransferThread;
import model.Snake;

/**
 * Created by Zsolt on 2015.03.14..
 * <p/>
 * An abstract class for managing other managers
 */
public abstract class GameManager {

    protected SnakeManager snakeOneManager;
    protected SnakeManager snakeTwoManager;
    protected FoodManager foodManager;
    protected TransferThread transferThread;

    public GameManager(SnakeManager snakeOneManager, SnakeManager snakeTwoManager, FoodManager foodManager) {
        this.snakeOneManager = snakeOneManager;
        this.snakeTwoManager = snakeTwoManager;
        this.foodManager = foodManager;
    }

    /**
     * Steps the game
     */
    public abstract void step();

    public Snake getSnakeOne() {
        return snakeOneManager.getSnake();
    }

    public Snake getSnakeTwo() {
        return snakeTwoManager.getSnake();
    }

    public SnakeManager getSnakeOneManager() {
        return snakeOneManager;
    }

    public SnakeManager getSnakeTwoManager() {
        return snakeTwoManager;
    }

    public FoodManager getFoodManager() {
        return foodManager;
    }

    public void setTransferThread(TransferThread transferThread) {
        this.transferThread = transferThread;
    }
}
