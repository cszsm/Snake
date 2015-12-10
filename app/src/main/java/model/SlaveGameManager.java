package model;

import connection.SnakePacket;
import model.enumeration.Direction;

/**
 * Created by Zsolt on 2015.04.21..
 * <p/>
 * Manages the game, if the device is slave
 */
public class SlaveGameManager extends GameManager {

    public SlaveGameManager(SnakeManager snakeOneManager, SnakeManager snakeTwoManager, FoodManager foodManager) {
        super(snakeOneManager, snakeTwoManager, foodManager);
    }

    @Override
    public void send() {
        sendPacket();
        snakeTwoManager.validateDirection();
    }

    /**
     * Steps the game...
     */
    public void step() {
        SnakePacket packet = getLastPacket();
        if (packet != null) {
            snakeOneManager.buildSnake(packet.getCorners());

            snakeOneManager.getSnake().setDirection(packet.getDirection());
            setDirection(packet.getDirection());
            snakeOneManager.validateDirection();

            foodManager.getFood().setX(packet.getFoodX());
            foodManager.getFood().setY(packet.getFoodY());
        }

            snakeOneManager.step();
            snakeTwoManager.step();

            if (!snakeOneManager.eat(foodManager.getFood())) {
                snakeOneManager.removeTail();
            }

            if (!snakeTwoManager.eat(foodManager.getFood())) {
                snakeTwoManager.removeTail();
            }
//        }
    }

    /**
     * Sets the SnakeManager's direction
     */
    private void setDirection(Direction direction) {
        switch (direction) {
            case RIGTH:
                snakeOneManager.setRight();
                break;
            case LEFT:
                snakeOneManager.setLeft();
                break;
            case DOWN:
                snakeOneManager.setDown();
                break;
            case UP:
                snakeOneManager.setUp();
                break;
            default:
                break;
        }
    }

    /**
     * Sends a packet with the direction and the food's coordinates
     */
    private void sendPacket() {
        SnakePacket packet = new SnakePacket(snakeTwoManager.getCorners(), snakeTwoManager.getDirection(), foodManager.getFood().getX(), foodManager.getFood().getY());
        transferThread.write(packet);
    }
}
