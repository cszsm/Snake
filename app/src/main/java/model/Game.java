package model;

import java.util.ArrayList;

import connection.ConnectionProperties;
import model.enumeration.Direction;

import static connection.enumeration.DeviceType.SERVER;

/**
 * Created by Zsolt on 2015.03.06..
 * <p/>
 * Represents a game
 * Singleton pattern
 */
public class Game {

    private static Game instance = null;

    private Board board;
    private GameManager gameManager;

    /**
     * Creates a standard board and sets the GameManager
     */
    protected Game() {
        board = new Board();

        SnakeManager snakeOneManager = new SnakeManager(createSnakeOne());
        SnakeManager snakeTwoManager = new SnakeManager(createSnakeTwo());
        FoodManager foodManager = new FoodManager(board, snakeOneManager.getSnake());

        if (ConnectionProperties.getInstance().getDeviceType() == SERVER) {
            gameManager = new MasterGameManager(snakeOneManager, snakeTwoManager, foodManager);
        } else {
            gameManager = new SlaveGameManager(snakeOneManager, snakeTwoManager, foodManager);
        }
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public Board getBoard() {
        return board;
    }

    public Snake getSnakeOne() {
        return gameManager.getSnakeOne();
    }

    public Snake getSnakeTwo() {
        return gameManager.getSnakeTwo();
    }

    public SnakeManager getSnakeOneManager() {
        return gameManager.getSnakeOneManager();
    }

    public SnakeManager getSnakeTwoManager() {
        return gameManager.getSnakeTwoManager();
    }

    public FoodManager getFoodManager() {
        return gameManager.getFoodManager();
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    /**
     * Resets the game
     */
    public void reset() {
        instance = null;
    }

    /**
     * Creates a snake with default coordinates
     */
    private Snake createSnakeOne() {
        ArrayList<Position> snake = new ArrayList<>();
        snake.add(new Position(5, 2));
        snake.add(new Position(4, 2));
        snake.add(new Position(3, 2));

        return new Snake(snake, Direction.RIGTH);
    }

    private Snake createSnakeTwo() {
        ArrayList<Position> snake = new ArrayList<>();
        snake.add(new Position(9, 6));
        snake.add(new Position(10, 6));
        snake.add(new Position(11, 6));

        return new Snake(snake, Direction.LEFT);
    }
}
