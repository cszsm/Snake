package control;

import connection.Packet;
import connection.SnakePacket;
import model.enumeration.Direction;

/**
 * Created by Zsolt on 2015.04.21..
 * <p/>
 * Manages the game, if the device is master
 */
public class MasterGameManager extends GameManager {

    public MasterGameManager(SnakeManager snakeOneManager, SnakeManager snakeTwoManager, FoodManager foodManager) {
        super(snakeOneManager, snakeTwoManager, foodManager);
    }

    /**
     * Steps the game and send a packet to the slave device
     */
    public void step() {
        SnakePacket packet = (SnakePacket) transferThread.getPacket();

        if (packet != null) {
            snakeTwoManager.getSnake().setDirection(packet.getDirection());
            setDirection(packet.getDirection());
        }

        sendPacket();

        snakeOneManager.step();
        snakeTwoManager.step();

        if (!snakeOneManager.eat(foodManager.getFood())) {
            snakeOneManager.removeTail();
        }

        if (!snakeTwoManager.eat(foodManager.getFood())) {
            snakeTwoManager.removeTail();
        }

        if (snakeOneManager.eat(foodManager.getFood()) || snakeTwoManager.eat(foodManager.getFood())) {
            foodManager.createFood(snakeOneManager.getSnake());
        }
    }

    /**
     * Sets the SnakeManager's direction
     */
    private void setDirection(Direction direction) {
        switch (direction) {
            case RIGTH:
                snakeTwoManager.setRight();
                break;
            case LEFT:
                snakeTwoManager.setLeft();
                break;
            case DOWN:
                snakeTwoManager.setDown();
                break;
            case UP:
                snakeTwoManager.setUp();
                break;
            default:
                break;
        }
    }

    /**
     * Sends a packet with the direction and the food's coordinates
     */
    private void sendPacket() {
        SnakePacket packet = new SnakePacket(snakeOneManager.getDirection(), foodManager.getFood().getX(), foodManager.getFood().getY());

        transferThread.write(packet);
    }
}
