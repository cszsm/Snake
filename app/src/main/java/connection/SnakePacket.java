package connection;

import java.io.Serializable;
import java.util.List;

import model.Position;
import model.enumeration.Direction;

/**
 * Created by zscse on 2015. 09. 29..
 *
 * A packet for sending the snake's attributes
 */
public class SnakePacket implements Serializable {

    private static int ids = 0;

    private List<Position> corners;
    private Direction direction;
    private int foodX;
    private int foodY;
    private int id;

    public SnakePacket(List<Position> corners, Direction direction, int foodX, int foodY) {
        this.corners = corners;
        this.direction = direction;
        this.foodX = foodX;
        this.foodY = foodY;
        id = ids;
        ids++;
    }

    public List<Position> getCorners() {
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

    public int getId() {
        return id;
    }
}
