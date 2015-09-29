package connection;

import model.enumeration.Direction;

/**
 * Created by zscse on 2015. 09. 29..
 */
public class SnakePacket extends Packet {
    private Direction direction;
    private int foodX;
    private int foodY;

    public SnakePacket(Direction direction, int foodX, int foodY) {
        this.direction = direction;
        this.foodX = foodX;
        this.foodY = foodY;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getFoodX() {
        return foodX;
    }

    public int getFoodY() {
        return foodY;
    }
}
