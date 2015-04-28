package model;

import android.util.Log;

import connection.Packet;

/**
 * Created by Zsolt on 2015.04.21..
 */
public class SlaveGameManager extends GameManager {

    public SlaveGameManager(SnakeManager snakeManager, FoodManager foodManager) {
        super(snakeManager, foodManager);
    }

    public void step() {

            Packet packet = transferThread.getPacket();
            if(packet != null) {
                Log.v("slave", packet.getDirection() + " - " + packet.getFoodX() + " - " + packet.getFoodY());
                snakeManager.getSnake().setDirection(packet.getDirection());
                switch (packet.getDirection()) {
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

                foodManager.getFood().setX(packet.getFoodX());
                foodManager.getFood().setY(packet.getFoodY());

                snakeManager.step();
                if (!snakeManager.eat(foodManager.getFood()))
                    snakeManager.removeTail();
            }
    }
}
