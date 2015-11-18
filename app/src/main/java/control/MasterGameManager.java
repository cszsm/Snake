package control;

import android.util.Log;

import java.util.Queue;

import connection.Packet;
import connection.SnakePacket;
import model.Snake;
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

    @Override
    public void send() {
        sendPacket();
        snakeOneManager.validateDirection();
    }

    /**
     * Steps the game and send a packet to the slave device
     */
    public void step() {
        Log.v("type", "MASTER");
//        SnakePacket packet = (SnakePacket) transferThread.getPacket();
        SnakePacket packet = getLastPacket();
        if (packet != null) {
            snakeTwoManager.buildSnake(packet.getCorners());
//            snakeTwoManager.setSnake(packet.getBody());
            snakeTwoManager.getSnake().setDirection(packet.getDirection());
            setDirection(packet.getDirection());
            snakeTwoManager.validateDirection();
        }

        snakeOneManager.step();
        snakeTwoManager.step();

        boolean snakeOneAte = snakeOneManager.eat(foodManager.getFood());
        if (!snakeOneAte) {
            snakeOneManager.removeTail();
        }

        boolean snakeTwoAte = snakeTwoManager.eat(foodManager.getFood());
        if (!snakeTwoAte) {
            snakeTwoManager.removeTail();
        }

        if (snakeOneAte || snakeTwoAte) {
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
//        Snake snake = new Snake(snakeOneManager.getSnake().getBody(), snakeOneManager.getSnake().getDirection());
//        SnakeManager snakeManager = new SnakeManager(snake);
//        snakeManager.step();
//        if (!snakeManager.eat(foodManager.getFood())) {
//            snakeManager.removeTail();
//        }
//
//        SnakePacket packet = new SnakePacket(snakeManager.getCorners(), snakeManager.getDirection(), foodManager.getFood().getX(), foodManager.getFood().getY());
        SnakePacket packet = new SnakePacket(snakeOneManager.getCorners(), snakeOneManager.getDirection(), foodManager.getFood().getX(), foodManager.getFood().getY());
//        SnakePacket packet = new SnakePacket(snakeOneManager.getSnake().getBody(), snakeOneManager.getDirection(), foodManager.getFood().getX(), foodManager.getFood().getY());
        transferThread.write(packet);
    }
}
