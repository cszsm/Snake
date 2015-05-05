package control;

import android.util.Log;

import connection.Packet;
import model.enumeration.Direction;

/**
 * Created by Zsolt on 2015.04.21..
 *
 * Manages the game, if the devies is slave
 */
public class SlaveGameManager extends GameManager {

    public SlaveGameManager(SnakeManager snakeManager, FoodManager foodManager) {
        super(snakeManager, foodManager);
    }

    /** Steps the game when a packet arrives  */
    public void step() {

        Packet packet = transferThread.getPacket();

        if(packet != null) {
            snakeManager.getSnake().setDirection(packet.getDirection());

            setDirection(packet.getDirection());

            foodManager.getFood().setX(packet.getFoodX());
            foodManager.getFood().setY(packet.getFoodY());

            snakeManager.step();
            if (!snakeManager.eat(foodManager.getFood()))
                snakeManager.removeTail();
        }
    }

    /** Sets the SnakeManager's direction */
    private void setDirection(Direction direction) {
        switch (direction) {
            case RIGTH:
                snakeManager.setRight();
                break;
            case LEFT:
                snakeManager.setLeft();
                break;
            case DOWN:
                snakeManager.setDown();
                break;
            case UP:
                snakeManager.setUp();
                break;
            default:
                break;
        }
    }
}
