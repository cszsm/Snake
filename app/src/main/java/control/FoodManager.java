package control;

import android.graphics.Point;

import java.util.Random;

import model.Board;
import model.Food;
import model.Snake;
import model.enumeration.BoardElement;

/**
 * Created by Zsolt on 2015.03.14..
 */
public class FoodManager {

    private Board board;
    private Food food;

    public FoodManager(Board board, Snake snake) {
        this.board = board;
        createFood(snake);
    }

    public Food getFood() {
        return food;
    }

    public void createFood(Snake snake) {
        Random random = new Random();
        int x = random.nextInt(15);
        int y = random.nextInt(9);
        while(!checkCoordinates(x, y, snake)) {
            x = random.nextInt(15);
            y = random.nextInt(9);
        }
        food = new Food(x, y);
    }

    private boolean checkCoordinates(int x, int y, Snake snake) {
        if(board.getFields()[x][y] == BoardElement.WALL)
            return false;
        for(Point p : snake.getBody())
            if(p.equals(x, y))
                return false;
        return true;
    }
}
