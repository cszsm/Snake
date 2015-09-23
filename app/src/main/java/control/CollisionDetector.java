package control;

import android.graphics.Point;

import model.Board;
import model.Snake;
import model.enumeration.BoardElement;

/**
 * Created by Zsolt on 2015.03.07..
 * <p/>
 * Detects collision between the snakeOne and a wall, or between two parts of the snakeOne
 */
public class CollisionDetector {

    private Board board;
    private Snake snakeOne;
    private Snake snakeTwo;

    public CollisionDetector(Board board, Snake snakeOne, Snake snakeTwo) {
        this.board = board;
        this.snakeOne = snakeOne;
        this.snakeTwo = snakeTwo;
    }

    /**
     * Detects collision
     */
    public boolean doesCollide() {
        if (board.getFields()[snakeOne.getHead().x][snakeOne.getHead().y] == BoardElement.WALL ||
                board.getFields()[snakeTwo.getHead().x][snakeTwo.getHead().y] == BoardElement.WALL) {
            return true;
        }

        Point headOne = snakeOne.getHead();
        Point headTwo = snakeTwo.getHead();

        for (Point point : snakeOne.getBody()) {
            if (headOne.equals(point) && headOne != point || headTwo.equals(point)) {
                return true;
            }
        }

        for (Point point : snakeTwo.getBody()) {
            if (headTwo.equals(point) && headTwo != point || headOne.equals(point)) {
                return true;
            }
        }

        return false;
    }
}
