package control;

/**
 * Created by Zsolt on 2015.04.21..
 * <p/>
 * Manages the single player game
 */
public class SingleGameManager extends GameManager {

    public SingleGameManager(SnakeManager snakeManager, FoodManager foodManager) {
        super(snakeManager, foodManager);
    }

    /**
     * Steps the game
     */
    public void step() {

        snakeManager.step();

        if (snakeManager.eat(foodManager.getFood())) {
            foodManager.createFood(snakeManager.getSnake());
        } else
            snakeManager.removeTail();
    }
}
