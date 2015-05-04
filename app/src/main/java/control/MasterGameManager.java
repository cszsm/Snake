package control;

import java.io.IOException;

import connection.Packet;
import connection.PacketSerialization;

/**
 * Created by Zsolt on 2015.04.21..
 */
public class MasterGameManager extends GameManager {

    public MasterGameManager(SnakeManager snakeManager, FoodManager foodManager) {
        super(snakeManager, foodManager);
    }

    public void step() {

        Packet packet = new Packet(snakeManager.getDirection(), foodManager.getFood().getX(), foodManager.getFood().getY());
        try {
            transferThread.write(PacketSerialization.serialize(packet));
        } catch (IOException e) {
            e.printStackTrace();
        }

        snakeManager.step();
        if (snakeManager.eat(foodManager.getFood())) {
            foodManager.createFood(snakeManager.getSnake());
        }
        else
            snakeManager.removeTail();
    }

//    private class DelayThread extends Thread {
//
//        @Override
//        public void run() {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            snakeManager.step();
//            if (snakeManager.eat(foodManager.getFood())) {
//                foodManager.createFood(snakeManager.getSnake());
//            }
//            else
//                snakeManager.removeTail();
//        }
//    }
}
