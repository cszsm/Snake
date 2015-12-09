package control;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

import model.Food;
import model.Position;
import model.Snake;
import model.enumeration.Direction;

/**
 * Created by Zsolt on 2015.03.14..
 * <p/>
 * Manages the snake
 */
public class SnakeManager {

    private Snake snake;
    private Direction direction;

    public SnakeManager(Snake snake) {
        this.snake = snake;
        direction = snake.getDirection();
    }

    public void validateDirection() {
        snake.setDirection(direction);
    }

    public void step() {
        snake.step();
    }

    public boolean eat(Food food) {
        return snake.getHead().equals(food.getX(), food.getY());
    }

    public void removeTail() {
        snake.removeTail();
    }

    public Snake getSnake() {
        return snake;
    }

    public void setRight() {
        if (snake.getDirection() != Direction.LEFT) {
            direction = Direction.RIGTH;
        }
    }

    public void setLeft() {
        if (snake.getDirection() != Direction.RIGTH) {
            direction = Direction.LEFT;
        }
    }

    public void setDown() {
        if (snake.getDirection() != Direction.UP) {
            direction = Direction.DOWN;
        }
    }

    public void setUp() {
        if (snake.getDirection() != Direction.DOWN) {
            direction = Direction.UP;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public ArrayList<Position> getCorners() {
        ArrayList<Position> body = snake.getBody();
        ArrayList<Position> corners = new ArrayList<>();
        corners.add(body.get(0));

        for(int i = 1; i < body.size() - 1; i++) {
            Position prev = body.get(i - 1);
            Position next = body.get(i + 1);
            if(prev.x != next.x && prev.y != next.y) {
                corners.add(body.get(i));
            }
        }

        corners.add(body.get(body.size() - 1));

        return corners;
    }

    public void buildSnake(List<Position> corners) {
        ArrayList<Position> body = new ArrayList<>();
        for (Position corner : corners) {
            if (body.isEmpty()) {
                body.add(corner);
            } else {
                Position tail = body.get(body.size() - 1);
                Position modifier = new Position();

                // count the direction of this section
                if(tail.x == corner.x) {
                    modifier.x = 0;
                    if(tail.y < corner.y) {
                        modifier.y = 1;
                    } else {
                        modifier.y = -1;
                    }
                } else {
                    modifier.y = 0;
                    if(tail.x < corner.x) {
                        modifier.x = 1;
                    } else {
                        modifier.x = -1;
                    }
                }

                // build the section
                while (!tail.equals(corner)) {
                    Position tmp = new Position();
                    tmp.x = tail.x + modifier.x;
                    tmp.y = tail.y + modifier.y;
                    body.add(tmp);
                    tail = tmp;
                }
            }
        }
        snake.setBody(body);
    }

    public void setSnake(ArrayList<Position> body) {
        snake.setBody(body);
    }
}
