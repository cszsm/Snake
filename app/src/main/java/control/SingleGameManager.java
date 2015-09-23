package control;

/**
 * Created by Zsolt on 2015.04.21..
 * <p/>
 * Manages the single player game
 */
public class SingleGameManager extends GameManager {

    public SingleGameManager(SnakeManager snakeOneManager, SnakeManager snakeTwoManager, FoodManager foodManager) {
        super(snakeOneManager, snakeTwoManager, foodManager);
    }

    /**
     * Steps the game
     */
    public void step() {
        snakeOneManager.step();

        if (snakeOneManager.eat(foodManager.getFood())) {
            foodManager.createFood(snakeOneManager.getSnake());
        } else
            snakeOneManager.removeTail();
    }
}
