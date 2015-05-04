package model;

import android.graphics.Point;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import connection.ConnectionManager;
import connection.enumeration.DeviceType;
import control.FoodManager;
import control.GameManager;
import control.MasterGameManager;
import control.SingleGameManager;
import control.SlaveGameManager;
import control.SnakeManager;

import static connection.enumeration.DeviceType.*;

/**
 * Created by Zsolt on 2015.03.06..
 *
 * Represents a game
 * Singleton pattern
 */
public class Game {
    private static Game instance = null;

    private Board board;
    private GameManager gameManager;

    /** Creates a standard board and sets the GameManager*/
    protected Game() {
        board = new Board();

        SnakeManager snakeManager = new SnakeManager(createSnake());
        FoodManager foodManager = new FoodManager(board, snakeManager.getSnake());

        if (ConnectionManager.getInstance().getDeviceType() == NONE)
            gameManager = new SingleGameManager(snakeManager, foodManager);
        else if (ConnectionManager.getInstance().getDeviceType() == MASTER)
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

    /** Resets the game */
    public void reset() {
        instance = null;
    }

    /** Creates a snake with default coordinates */
    private Snake createSnake() {
        ArrayList<Point> snake = new ArrayList<>();
        snake.add(new Point(5, 2));
        snake.add(new Point(4, 2));
        snake.add(new Point(3, 2));

        return new Snake(snake);
    }
}
