package model;

import android.graphics.Point;

import java.util.ArrayList;

import model.enumeration.Direction;

/**
 * Created by Zsolt on 2015.03.06..
 */
public class Snake {
    private ArrayList<Point> body;
    private Direction direction;

    public Snake(ArrayList<Point> start) {
        body = start;

        direction = Direction.RIGTH;
    }

    public ArrayList<Point> getBody() {
        return body;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void step() {
        Point head = getHead();

        switch (direction) {
            case LEFT:
                body.add(0, new Point(head.x - 1, head.y));
                break;
            case RIGTH:
                body.add(0, new Point(head.x + 1, head.y));
                break;
            case UP:
                body.add(0, new Point(head.x, head.y - 1));
                break;
            case DOWN:
                body.add(0, new Point(head.x, head.y + 1));
                break;
            default:
                break;
        }
    }

    public Point getHead() {
        return body.get(0);
    }

    public void removeTail() {
        body.remove(body.size() - 1);
    }
}
