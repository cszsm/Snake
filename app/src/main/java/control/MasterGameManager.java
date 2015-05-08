package control;

import java.io.IOException;

import connection.Packet;
import connection.PacketSerialization;

/**
 * Created by Zsolt on 2015.04.21..
 * <p/>
 * Manages the game, if the device is master
 */
public class MasterGameManager extends GameManager {

    public MasterGameManager(SnakeManager snakeManager, FoodManager foodManager) {
        super(snakeManager, foodManager);
    }

    /**
     * Steps the game and send a packet to the slave device
     */
    public void step() {

        sendPacket();

        snakeManager.step();

        if (snakeManager.eat(foodManager.getFood())) {
            foodManager.createFood(snakeManager.getSnake());
        } else
            snakeManager.removeTail();
    }

    /**
     * Sends a packet with the direction and the food's coordinates
     */
    private void sendPacket() {

        Packet packet = new Packet(snakeManager.getDirection(), foodManager.getFood().getX(), foodManager.getFood().getY());

        try {
            transferThread.write(PacketSerialization.serialize(packet));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
