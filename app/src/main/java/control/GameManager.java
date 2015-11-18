package control;

import connection.ConnectionManager;
import connection.SnakePacket;
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

        transferThread = ConnectionManager.getInstance().getTransferThread();
    }

    /**
     * Sends the current direction...
     */
    public abstract void send();

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

    protected SnakePacket getLastPacket() {
        SnakePacket packet = (SnakePacket) transferThread.getPacket();
        for (int i = 0; i < transferThread.getQueueLength() - 1; i++) {
            SnakePacket tmp = (SnakePacket) transferThread.getPacket();
            if(packet.getId() < tmp.getId()) {
                packet = tmp;
            }
        }
        return packet;
    }
}
