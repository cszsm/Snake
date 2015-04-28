package model;

import model.enumeration.Direction;

/**
 * Created by Zsolt on 2015.03.14..
 */
public class SnakeManager {

    private Snake snake;
    private Direction direction;

    public SnakeManager(Snake snake) {
        this.snake = snake;
        direction = snake.getDirection();
    }

    public void step() {
        snake.setDirection(direction);
        snake.step();
    }

    public boolean eat(Food food) {
        if(snake.getHead().equals(food.getX(), food.getY()))
            return true;
        return false;
    }

    public void removeTail() {
        snake.removeTail();
    }

    public Snake getSnake() {
        return snake;
    }

    public void setRight() {
        if(snake.getDirection() != Direction.LEFT)
            direction = Direction.RIGTH;
    }

    public void setLeft() {
        if(snake.getDirection() != Direction.RIGTH)
            direction = Direction.LEFT;
    }

    public void setDown() {
        if(snake.getDirection() != Direction.UP)
            direction = Direction.DOWN;
    }

    public void setUp() {
        if(snake.getDirection() != Direction.DOWN)
            direction = Direction.UP;
    }

    public Direction getDirection() {
        return direction;
    }
}
