package control;

import android.graphics.Point;

import model.Board;
import model.Position;
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

        Position headOne = snakeOne.getHead();
        Position headTwo = snakeTwo.getHead();

        for (Position position : snakeOne.getBody()) {
            if (headOne.equals(position) && headOne != position || headTwo.equals(position)) {
                return true;
            }
        }

        for (Position position : snakeTwo.getBody()) {
            if (headTwo.equals(position) && headTwo != position || headOne.equals(position)) {
                return true;
            }
        }

        return false;
    }
}
