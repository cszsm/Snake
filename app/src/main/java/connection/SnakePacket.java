package connection;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

import model.Position;
import model.enumeration.Direction;

/**
 * Created by zscse on 2015. 09. 29..
 */
public class SnakePacket extends Packet {
    private List<Position> corners;
    private ArrayList<Position> body;
    private Direction direction;
    private int foodX;
    private int foodY;

    public SnakePacket(List<Position> corners, Direction direction, int foodX, int foodY) {
        this.corners = corners;
        this.direction = direction;
        this.foodX = foodX;
        this.foodY = foodY;
    }

//    public SnakePacket(ArrayList<Position> body, Direction direction, int foodX, int foodY) {
//        this.body = body;
//        this.direction = direction;
//        this.foodX = foodX;
//        this.foodY = foodY;
//    }

    public List<Position> getCorners() {
        return corners;
    }

    public ArrayList<Position> getBody() {
        return body;
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
