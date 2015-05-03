package model;

import android.graphics.Point;

import java.util.ArrayList;

import connection.ConnectionManager;
import connection.enumeration.DeviceType;

/**
 * Created by Zsolt on 2015.03.06..
 */
public class Game {
    private static Game instance = null;

    private Board board;
    private GameManager gameManager;

    protected Game() {
        board = new Board();

        ArrayList<Point> snake = new ArrayList<>();
        snake.add(new Point(5, 2));
        snake.add(new Point(4, 2));
        snake.add(new Point(3, 2));

        SnakeManager snakeManager = new SnakeManager(new Snake(snake));
        FoodManager foodManager = new FoodManager(board, snakeManager.getSnake());

        if (ConnectionManager.getInstance().getDeviceType() == DeviceType.NONE)
            gameManager = new SingleGameManager(snakeManager, foodManager);
        else if (ConnectionManager.getInstance().getDeviceType() == DeviceType.MASTER)
            gameManager = new MasterGameManager(snakeManager, foodManager);
        else
            gameManager = new SlaveGameManager(snakeManager, foodManager);
    }

    public static Game getInstance() {
        if (instance == null)
            instance = new Game();
        return instance;
    }

    public Board getBoard() {
        return board;
    }

    public Snake getSnake() {
        return gameManager.getSnake();
    }

    public SnakeManager getSnakeManager() {
        return gameManager.getSnakeManager();
    }

    public FoodManager getFoodManager() {
        return gameManager.getFoodManager();
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public void reset() {
        instance = null;
    }
}
