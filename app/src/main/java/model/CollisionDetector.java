package model;

import android.graphics.Point;

import model.Board;
import model.enumeration.BoardElement;
import model.Snake;

/**
 * Created by Zsolt on 2015.03.07..
 */
public class CollisionDetector {
    private Board board;
    private Snake snake;

    public CollisionDetector(Board board, Snake snake) {
        this.board = board;
        this.snake = snake;
    }

    public boolean doesCollide() {
        if(board.getFields()[snake.getHead().x][snake.getHead().y] == BoardElement.WALL)
            return true;

        Point head = snake.getHead();
        for(Point point : snake.getBody())
            if(head.equals(point) && head != point)
                return true;
        return false;
    }
}
