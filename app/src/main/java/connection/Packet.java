package connection;

import java.io.Serializable;

import model.enumeration.Direction;

/**
 * Created by Zsolt on 2015.03.24..
 *
 * A packet which can be transferred between devices
 */
public class Packet implements Serializable {

    private Direction direction;
    private int foodX;
    private int foodY;

    public Packet(Direction direction, int foodX, int foodY) {
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
