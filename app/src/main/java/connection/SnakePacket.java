package connection;

import android.graphics.Point;

import java.util.List;

import model.enumeration.Direction;

/**
 * Created by zscse on 2015. 09. 29..
 */
public class SnakePacket extends Packet {
    private List<Point> corners;
    private Direction direction;
    private int foodX;
    private int foodY;

    public SnakePacket(List<Point> corners, Direction direction, int foodX, int foodY) {
        this.corners = corners;
        this.direction = direction;
        this.foodX = foodX;
        this.foodY = foodY;
    }

    public List<Point> getCorners() {
        return corners;
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
